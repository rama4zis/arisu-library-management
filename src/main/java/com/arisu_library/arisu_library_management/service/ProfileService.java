package com.arisu_library.arisu_library_management.service;

import com.arisu_library.arisu_library_management.model.app.SimpleMap;
import com.arisu_library.arisu_library_management.model.request.ProfileRequestRecord;

public interface ProfileService {
    void editProfile(ProfileRequestRecord req);

    SimpleMap findByUserId(String userId);
}
