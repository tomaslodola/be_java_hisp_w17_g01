package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.factory.UserFactory;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoryImp userRepository;

    @InjectMocks
    UserService userService;

    private static final Integer userId = 6;
    private static final Integer userFollower1=1;
    private static final Integer userFollower2=2;
    private static final Integer userFollower3=3;
    private static final String asc = "name_asc";
    private static final String desc = "name_desc";
    private static final String invalid_order = "name_";

    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers asc en FollowersList")
    public void testUS4A(){
        // Arrange
        // Obtenemos una lista ordenada de manera ascendente
        UserFollowersOutputListDTO userFollowersExpectedSortedAsc = UserFactory.createUserFollowersOutputListDTOSortAsc();
        MockGetUser();
        MockGetFollowersUsers();

        // Act
        UserFollowersOutputListDTO userActual = userService.getFollowersList(userId,asc);
        // Assert
        Assertions.assertEquals(userFollowersExpectedSortedAsc,userActual);
    }


    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers desc en FollowersList")
    public void testUS4B(){
        // Arrange
        UserFollowersOutputListDTO userFollowersExpectedSortedDesc = UserFactory.createUserFollowersOutputListDTOSortDesc();
        MockGetUser();
        MockGetFollowersUsers();
        // Act
        UserFollowersOutputListDTO userActual = userService.getFollowersList(userId,desc);
        // Assert
        Assertions.assertEquals(userFollowersExpectedSortedDesc,userActual);
    }

    @Test
    @DisplayName("Verificar que el tipo de ordenamiento alfabético no exista en FollowersList")
    public void testUS3A(){
        // Arrange
        MockGetUser();
        MockGetFollowersUsers();
        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                ()-> userService.getFollowersList(userId,invalid_order)
        );
    }


    @Test
    @DisplayName("Verificar que el tipo de ordenamiento alfabético no exista en FollowedList")
    public void testUS3B(){
        // Arrange
        MockGetUser();
        MockGetFollowedUsers();
        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                ()-> userService.getFollowedList(userId,invalid_order)
        );
    }

    @Test
    @DisplayName("Verificar ordenamiento alfabetico followed asc en FollowedList")
    public void testUS4C(){
        // Arrange
        // Obtenemos una lista ordenada de manera ascendente
        UserFollowedOutputListDTO userFollowedExpectedSortedAsc = UserFactory.createUserFollowedOutputListDTOSortAsc();
        MockGetUser();
        MockGetFollowersUsers();

        // Act
        UserFollowedOutputListDTO userActual = userService.getFollowedList(userId,asc);
        // Assert
        Assertions.assertEquals(userFollowedExpectedSortedAsc,userActual);
    }


    @Test
    @DisplayName("Verificar ordenamiento alfabetico followers desc en FollowedList")
    public void testUS4D(){
        // Arrange
        UserFollowedOutputListDTO userFollowedExpectedSortedDesc = UserFactory.createUserFollowedOutputListDTOSortDesc();
        MockGetUser();
        MockGetFollowersUsers();
        // Act
        UserFollowedOutputListDTO userActual = userService.getFollowedList(userId,desc);
        // Assert
        Assertions.assertEquals(userFollowedExpectedSortedDesc,userActual);
    }

    @Test
    @DisplayName("Verificar que el usuario a seguir exista. (US-0001) caso donde se cumple: ")
    public void  testUS1A(){
        //Arrange
        Mockito.when(userRepository.getUser(4)).thenReturn(UserFactory.createUser());
        Mockito.when(userRepository.getUser(1)).thenReturn(UserFactory.createUser());

        //act
        MessageResponseDTO obtained = userService.followUser(1,4);
        //assert
        assertEquals(obtained,new MessageResponseDTO("Se ha seguido al usuario "+ 4 + " con exito."));

    }

    @Test
    @DisplayName("Verificar que el usuario a dejar de seguir exista. (US-0007) caso donde se cumple: ")
    public void testUS7A(){
        //Arrange
        Mockito.when(userRepository.getUser(2)).thenReturn(UserFactory.createUser());
        Mockito.when(userRepository.getUser(1)).thenReturn(UserFactory.createUser());
        //act
        MessageResponseDTO followFirst = userService.followUser(1,2);
        MessageResponseDTO obtained = userService.unfollowUser(1,2);
        //assert
        assertEquals(obtained, new MessageResponseDTO("Se ha dejado de seguir al usuario " + 2 + " con exito."));
    }


    @Test
    @DisplayName("Verificar que la cantidad de seguidores de un determinado usuario sea correcta. (US-0002)")
    public void testUS2(){
        //Arrange
        Mockito.when(userRepository.getUser(6)).thenReturn(UserFactory.createJsonUser());
        //act
        UserCountFollowersDTO obtained = userService.countNumberOfFollowers(6);
        //assert
        assertEquals(new UserCountFollowersDTO(3),obtained);
    }

    private void MockGetUser(){
        // Mockeamos el usuario obtenido para realizar el ordenamiento
        Mockito.when(userRepository.getUser(userId)).thenReturn(UserFactory.createJsonUser());
    }

    private void MockGetFollowersUsers(){
        // Mockeamos los usuarios que sigue al usuario principal
        Mockito.when(userRepository.getUser(userFollower1)).thenReturn(UserFactory.user1());
        Mockito.when(userRepository.getUser(userFollower2)).thenReturn(UserFactory.user2());
        Mockito.when(userRepository.getUser(userFollower3)).thenReturn(UserFactory.user3());
    }

    private void MockGetFollowedUsers(){
        // Mockeamos los usuarios que sigue al usuario principal
        Mockito.when(userRepository.getUser(userFollower1)).thenReturn(UserFactory.user1());
        Mockito.when(userRepository.getUser(userFollower2)).thenReturn(UserFactory.user2());
        Mockito.when(userRepository.getUser(userFollower3)).thenReturn(UserFactory.user3());
    }
}
