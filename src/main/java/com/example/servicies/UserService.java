package com.example.servicies;

import com.example.DTOs.UserInDTO;
import com.example.DTOs.UserOutDTO;
import com.example.entities.UserEntity;
import com.example.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserOutDTO create(UserInDTO userInDTO) throws Exception {
        Calendar current = Calendar.getInstance();
        UserEntity userEntity;

        validateUserDTO(userInDTO);
        userEntity = convert(userInDTO, UserEntity.class);

        try {
            userEntity.setUuid(UUID.randomUUID().toString());
            userEntity.setCreatedAt(new Timestamp(current.getTimeInMillis()));
            userEntity.setSoftDelete(false);

            userEntity = userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Can't create entity. : " + e.getMessage());
        }
        return convert(userEntity, UserOutDTO.class);
    }

    public UserOutDTO update(long userId, UserInDTO userInDTO) throws Exception {
        Calendar current = Calendar.getInstance();
        UserEntity oldUserEntity;
        UserEntity newUserEntity;

        oldUserEntity = userRepository.findOne(userId);

        if (oldUserEntity == null) {
            throw new Exception("Error: can't find the userId");
        }

        validateUserDTO(userInDTO);
        newUserEntity = convert(userInDTO, UserEntity.class);

        newUserEntity.setUserId(oldUserEntity.getUserId());
        newUserEntity.setUuid(oldUserEntity.getUuid());
        newUserEntity.setCreatedAt(oldUserEntity.getCreatedAt());
        newUserEntity.setModifiedAt(new Timestamp(current.getTimeInMillis()));
        newUserEntity.setSoftDelete(false);

        try {
            newUserEntity.setSoftDelete(false);

            newUserEntity = userRepository.save(newUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Can't update entity. : " + e.getMessage());
        }
        return convert(newUserEntity, UserOutDTO.class);
    }

    public UserOutDTO softDelete(long userId) throws Exception {
        UserEntity userEntity = new UserEntity();

        userEntity = userRepository.findOne(userId);

        if (userEntity == null) {
            throw new Exception("Error: can't find the userId to delete");
        }

        try {
            userEntity.setSoftDelete(true);
            userEntity = userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error: Can't update entity. : " + e.getMessage());
        }
        return convert(userEntity, UserOutDTO.class);
    }

    public void delete(long UserId) {
        userRepository.delete(UserId);
    }

    public List<UserOutDTO> listAll() {

        List<UserOutDTO> userOutDTOList = new ArrayList<>();
        Iterable<UserEntity> UserEntites = userRepository.findAll();


        for (UserEntity userEntity : UserEntites) {
            log.debug("User: ", userEntity.toString());
            userOutDTOList.add(convert(userEntity, UserOutDTO.class));
        }
        return userOutDTOList;
    }

    public List<UserOutDTO> search(String name) {

        List<UserOutDTO> userOutDTOList = new ArrayList<>();
        List<UserEntity> UserEntites = userRepository.findByNameIgnoreCaseContaining(name);

        for (UserEntity userEntity : UserEntites) {
            log.debug("User: ", userEntity.toString());
            userOutDTOList.add(convert(userEntity, UserOutDTO.class));
        }
        return userOutDTOList;
    }


    public UserOutDTO findById(long userId) throws Exception {

        UserEntity userEntity = userRepository.findOne(userId);

        if (userEntity == null) {
            throw new Exception("Error: can't find the userId:");
        }
        return convert(userEntity, UserOutDTO.class);
    }

    public UserOutDTO findByUuid(String uuid) throws Exception {

        UserEntity userEntity = userRepository.findByUuid(uuid);

        if (userEntity == null) {
            throw new Exception("Error: can't find the uuid:");
        }
        return convert(userEntity, UserOutDTO.class);
    }


    public UserEntity findByName(String name) throws Exception {

        UserEntity userEntity = userRepository.findByName(name);

        if (userEntity == null) {
            throw new Exception("Error: can't find the name:");
        }
        return userEntity;
    }


    private void validateUserDTO(UserInDTO UserInDTO) throws Exception {
        if (UserInDTO.getName() == null || Objects.equals(UserInDTO.getName(), "")) {
            throw new Exception("Name value can't be blank");
        }
        if (UserInDTO.getDescription() == null || Objects.equals(UserInDTO.getDescription(), "")) {
            throw new Exception("Description value can't be blank");
        }
    }

    private <T, K> T convert(K rawData, Class<T> converToClass) {
        return modelMapper.map(rawData, converToClass);
    }
}