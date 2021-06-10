package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key_, password, userid) " +
            "VALUES (#{url}, #{username}, #{key_}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential cr);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
    Credential getCredentialById(int id);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials();

    @Update("UPDATE CREDENTIALS SET url=#{cr.url}, username=#{cr.username}, key_=#{cr.key_}, " +
            "password=#{cr.password} WHERE credentialid=#{id}")
    int updateCredential(@Param("id") int id, @Param("cr") Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    int deleteCredential(int id);
}
