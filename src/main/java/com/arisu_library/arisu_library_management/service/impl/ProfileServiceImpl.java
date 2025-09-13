package com.arisu_library.arisu_library_management.service.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arisu_library.arisu_library_management.entity.Profile;
import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.ProfileRequestRecord;
import com.arisu_library.arisu_library_management.repository.ProfileRepository;
import com.arisu_library.arisu_library_management.repository.UserRepository;
import com.arisu_library.arisu_library_management.service.ProfileService;
import com.arisu_library.arisu_library_management.service.ValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Override
    public Profile editProfile(ProfileRequestRecord req) {
        validationService.validate(req);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Profile target;
        if (isAdmin && req.id() != null) {
            target = profileRepository.findById(req.id())
                    .orElseThrow(() -> new RuntimeException("Profile not found"));
        } else {
            // Self-service: always edit the authenticated user's profile
            var user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new AccessDeniedException("User not authenticated"));
            target = user.getProfile();
            if (target == null) {
                Profile newProfile = new Profile();
                newProfile.setUser(user);
                target = newProfile;
            }
            if (req.id() != null && !req.id().equals(target.getId())) {
                throw new AccessDeniedException("You can only edit your own profile");
            }
        }

        target.setFirstName(req.firstName());
        target.setLastName(req.lastName());
        target.setDob(req.dob());
        target.setAddress(req.address());
        target.setCity(req.city());
        target.setPhone(req.phone());

        return profileRepository.save(target);
    }

    @Override
    public SimpleMap findByUserId(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Profile for user not found");
        }

        return SimpleMap.createMap()
                .add("id", profile.getId())
                .add("firstName", profile.getFirstName())
                .add("lastName", profile.getLastName())
                .add("dob", profile.getDob())
                .add("address", profile.getAddress())
                .add("city", profile.getCity())
                .add("phone", profile.getPhone())
                .add("userId", user.getId())
                .add("username", user.getUsername())
                .add("email", user.getEmail());
    }
}
