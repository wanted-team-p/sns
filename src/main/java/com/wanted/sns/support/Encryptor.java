package com.wanted.sns.support;

import com.wanted.sns.domain.*;

public interface Encryptor {

    String encrypt(PlainPassword password);
}
