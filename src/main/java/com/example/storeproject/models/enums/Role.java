package com.example.storeproject.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

   ROLE_USER,ROLE_ADMIN;//юзаем тип enum просто для хранения каких либо констант

   @Override
   public String getAuthority() {
      return name();//возвращаем роль в строковом виде(метод класса enum.java)
   }
}
