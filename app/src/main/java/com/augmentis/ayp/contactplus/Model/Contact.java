package com.augmentis.ayp.contactplus.Model;

import java.util.UUID;

/**
 * Created by Apinya on 8/9/2016.
 */
public class Contact {
    private UUID Id;
    private String Name;
    private String Tel;
    private String Email;

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID Id) {
        this.Id = Id;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        this.Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhotoFilename(){

        return "IMG_" + getId().toString() + ".jpg";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("uuid =").append(Id);
        builder.append("name =").append(Name);
        builder.append("tel =").append(Tel);
        builder.append("email =").append(Email);
        return builder.toString();
    }
}
