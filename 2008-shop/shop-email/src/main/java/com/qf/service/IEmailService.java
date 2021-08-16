package com.qf.service;


import com.qf.entity.Email;

import javax.mail.MessagingException;

public interface IEmailService {

    public void sendEmail(Email email) throws MessagingException;

}
