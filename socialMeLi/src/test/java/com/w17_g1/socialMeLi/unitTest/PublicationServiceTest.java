package com.w17_g1.socialMeLi.unitTest;

import com.w17_g1.socialMeLi.dto.output.Publication.PublicationListDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationOutDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.publication.PublicationRepositoryImp;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.w17_g1.socialMeLi.factory.PublicationFactory.*;
import static com.w17_g1.socialMeLi.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {
    @Mock
    PublicationRepositoryImp publicationRepository;

    @Mock
    UserRepositoryImp userRepositoryImp;

    @InjectMocks
    PublicationService publicationService;

    private static final Integer mainUserID = 1;
    private static final Integer firstFollowedID = 100;
    private static final Integer secondFollowedID = 200;
    private static final String asc = "date_asc";
    private static final String desc = "date_desc";
    private static final LocalDate limitDate = LocalDate.now().minusDays(15);

    @Test
    @DisplayName("No puede haber publicaciones de seguido por el usuario si el usuario no sigue a nadie")
    public void test01(){
        // Arrange
        mockGetUser(createUserWhoFollowsNoOne(mainUserID));

        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                () -> publicationService.getLatestPublicationsFromUser(mainUserID,asc)
        );
    }

    @Test
    @DisplayName("Ultimas publicaciones de los seguidos del usuario no pueden tener un orden invalido")
    public void test02(){
        // Arrange
        mockGetUser(createUserWhoFollowsOneUser(mainUserID,firstFollowedID));

        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                () -> publicationService.getLatestPublicationsFromUser(mainUserID,"invalid_order")
        );
    }

    @Test
    @DisplayName("De no haber publicaciones para mostrar, se debe disparar una excepcion")
    public void test03(){
        // Arrange
        mockGetUser(createUserWhoFollowsOneUser(mainUserID,firstFollowedID));
        mockPublicationFromUser(firstFollowedID,new ArrayList<>());

        // Assert
        Assertions.assertThrows(
                ElementNotFoundException.class,
                () -> publicationService.getLatestPublicationsFromUser(mainUserID,asc)
        );
    }

    @Test
    @DisplayName("Usuario siguiendo un usuario: Ordenamiento ascendente")
    public void test04(){
        // Arrange
        List<PublicationOutDTO> listOfPostsDTO = arrangeSortingTestWithOneFollowed(true);
        PublicationListDTO expected = new PublicationListDTO(mainUserID, listOfPostsDTO);

        // Act
        PublicationListDTO obtained = publicationService.getLatestPublicationsFromUser(mainUserID,asc);

        // Assert
        assertEquals(expected,obtained);
    }

    @Test
    @DisplayName("Usuario siguiendo un usuario: Ordenamiento descendente")
    public void test05(){
        // Arrange
        List<PublicationOutDTO> listOfPostsDTO = arrangeSortingTestWithOneFollowed(false);
        PublicationListDTO expected = new PublicationListDTO(mainUserID, listOfPostsDTO);

        // Act
        PublicationListDTO obtained = publicationService.getLatestPublicationsFromUser(mainUserID,desc);

        // Assert
        assertEquals(expected,obtained);
    }

    @Test
    @DisplayName("Usuario siguiendo 2 usuarios: Ordenamiento ascendente")
    public void test06(){
        // Arrange
        List<PublicationOutDTO> listOfPostsDTO = arrrangeSortingTestWithTwoFollowed(true);
        PublicationListDTO expected = new PublicationListDTO(mainUserID, listOfPostsDTO);

        // Act
        PublicationListDTO obtained = publicationService.getLatestPublicationsFromUser(mainUserID,asc);

        // Assert
        assertEquals(expected,obtained);
    }

    @Test
    @DisplayName("Usuario siguiendo 2 usuarios: Ordenamiento descendente")
    public void test07() {
        // Arrange
        List<PublicationOutDTO> listOfPostsDTO = arrrangeSortingTestWithTwoFollowed(false);
        PublicationListDTO expected = new PublicationListDTO(mainUserID, listOfPostsDTO);

        // Act
        PublicationListDTO obtained = publicationService.getLatestPublicationsFromUser(mainUserID,desc);

        // Assert
        assertEquals(expected,obtained);
    }

    @Test
    @DisplayName("No se listan publicaciones con mas de 2 semanas de antiguedad")
    public void test08() {
        // Arrange
        List<PublicationOutDTO> listOfPostsDTO = arrangeDateTest();
        PublicationListDTO expected = new PublicationListDTO(mainUserID, listOfPostsDTO);

        // Act
        PublicationListDTO obtained = publicationService.getLatestPublicationsFromUser(mainUserID,asc);

        // Assert
        assertEquals(expected,obtained);
    }

    private void mockGetUser(User mainUser){
        //Se hace un Mock del metodo getUser de userRepository
        Mockito.when( userRepositoryImp.getUser(mainUserID) ).thenReturn( mainUser );
    }

    private void mockPublicationFromUser(Integer userID, List<Publication> postList){
        //Se hace un Mock del metodo getPublicationsFromUser de publicationRepository
        Mockito.when( publicationRepository.getPublicationsFromUser(userID,limitDate)).thenReturn(postList);
    }

    private List<PublicationOutDTO> arrangeSortingTestWithOneFollowed(boolean ascSorted){
        // Arrange
        // Definimos el usuario principal correspondiente (con dos seguidos) y mockeamos el getUser
        User mainUser = createUserWhoFollowsOneUser(mainUserID, firstFollowedID);
        mockGetUser(mainUser);

        // Creamos 3 publicaciones separadas por 4 dias cada una (todas dentro del rango de 20 dias)
        Publication oldPublication = createPublicationForUser(firstFollowedID, limitDate.plusDays(4));
        Publication recentPublication = createPublicationForUser(firstFollowedID, limitDate.plusDays(8));
        Publication newPublication = createPublicationForUser(firstFollowedID, limitDate.plusDays(12));

        // Mockeamos las publicaciones de los usuarios seguidos
        // Uno de los usuario tendra la publicacion mas vieja y nueva
        // El otro tendra la publicacion intermedia
        mockPublicationFromUser(firstFollowedID, List.of(recentPublication,oldPublication,newPublication));

        // Finalmente devovlemos el DTO esperado conformado del ID del usuario
        // la lista de publicaciones ordenada correctamente
        return (ascSorted)? getAscendentList(oldPublication,recentPublication,newPublication) :
                getDescendentList(oldPublication,recentPublication,newPublication);
    }

    private List<PublicationOutDTO> arrrangeSortingTestWithTwoFollowed(boolean ascSorted){
        // Arrange
        // Definimos el usuario principal correspondiente (con dos seguidos) y mockeamos el getUser
        User mainUser = createUserWhoFollowsTwoUsers(mainUserID, firstFollowedID, secondFollowedID);
        mockGetUser(mainUser);

        // Creamos 3 publicaciones separadas por 4 dias cada una (todas dentro del rango de 20 dias)
        Publication oldPublication = createPublicationForUser(secondFollowedID, limitDate.plusDays(4));
        Publication recentPublication = createPublicationForUser(firstFollowedID, limitDate.plusDays(8));
        Publication newPublication = createPublicationForUser(secondFollowedID, limitDate.plusDays(12));

        // Mockeamos las publicaciones de los usuarios seguidos
        // Uno de los usuario tendra la publicacion mas vieja y nueva
        // El otro tendra la publicacion intermedia
        mockPublicationFromUser(firstFollowedID, List.of(recentPublication));
        mockPublicationFromUser(secondFollowedID,List.of(oldPublication,newPublication));

        // Finalmente devovlemos el DTO esperado conformado del ID del usuario
        // la lista de publicaciones ordenada correctamente
        return (ascSorted)? getAscendentList(oldPublication,recentPublication,newPublication) :
                                getDescendentList(oldPublication,recentPublication,newPublication);
    }

    private List<PublicationOutDTO> arrangeDateTest(){
        // Definimos el usuario principal correspondiente (siguiendo un usuario) y mockeamos el getUser
        User mainUser = createUserWhoFollowsOneUser(mainUserID, firstFollowedID);
        mockGetUser(mainUser);

        // Creamos 2 publicaciones:
        // Una publicacion expirada (mas antigua que la fecha de vencimiento de publicaciones)
        // Una publicacion nueva (dentro de la fecha de vencimiento de publicaciones)
        Publication expiredPublication = createPublicationForUser(firstFollowedID, limitDate.minusDays(15));
        Publication newPublication = createPublicationForUser(firstFollowedID, limitDate.plusDays(4));

        // Mockeamos PublicationsFromUser para que devuelve la publicacion no expirada
        mockPublicationFromUser(firstFollowedID, List.of(newPublication));

        // Finalmente devovlemos el DTO esperado conformado del ID del usuario
        // la lista de publicaciones ordenada correctamente
        return List.of(outDTOFromPublication(newPublication));
    }

}
