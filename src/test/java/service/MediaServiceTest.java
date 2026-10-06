package service;


import at.technikum.dto.request.CreateMediaEntryDTO;
import at.technikum.dto.request.CreateRatingDTO;
import at.technikum.model.*;
import at.technikum.repository.interfaces.IMediaRepo;
import at.technikum.repository.interfaces.IRatingRepo;
import at.technikum.repository.interfaces.IUserRepo;
import at.technikum.service.MediaService;
import at.technikum.util.exception.InputValidationException;
import at.technikum.util.exception.MediaException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest extends MockitoExtension {

    @Mock
    private IMediaRepo mediaRepo;
    @Mock
    private IUserRepo userRepo;
    @Mock
    private IRatingRepo ratingRepo;

    @InjectMocks
    private MediaService service;

    private User creator;
    private User user;
    private MediaEntry entry;

    @BeforeEach
    public void initTestData(){
        creator = new User("TestCreator","supersecret");
        user = new User("TestUser","supersecret123");
        userRepo.save(creator);
        userRepo.save(user);

        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.Adventure);
        genres.add(Genre.Crime);
        genres.add(Genre.Fantasy);

        entry = new MovieEntry(creator.getId(),"TestMedia","This is a Test Media Entry", genres, LocalDate.of(1980,5,21),12);
        entry.addRating(new Rating(UUID.randomUUID(),entry.getId(),3,null));
        entry.addRating(new Rating(user.getId(),entry.getId(),1,"This Has A COMMENT"));

        mediaRepo.save(entry);
    }

    @Test
    public void getMediaById_fromExistingEntry_shouldReturnEntry(){
        when(mediaRepo.findById(entry.getId())).thenReturn(Optional.of(entry));
        MediaEntry result = service.getMediaEntry(entry.getId());
        assertEquals(result.getId(),entry.getId());
    }

    @Test
    public void deleteMedia_asNonCreator_sholdThrow(){
        when(mediaRepo.findById(entry.getId())).thenReturn(Optional.of(entry));
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(MediaException.class, ()->service.deleteMediaEntry(user.getId(),entry.getId()));

        verify(mediaRepo, never()).delete(entry.getId());
    }

    @Test
    public void deleteMedia_asCreator_shouldDeleteEntry(){
        when(mediaRepo.findById(entry.getId())).thenReturn(Optional.of(entry));
        when(userRepo.findById(creator.getId())).thenReturn(Optional.of(creator));

        assertDoesNotThrow(()->service.deleteMediaEntry(creator.getId(),entry.getId()));

        verify(mediaRepo).delete(entry.getId());
    }

    @Test
    public void createMedia_withValidDto_shouldCreate(){
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        MediaEntry result = service.createMediaEntry(user.getId(),new CreateMediaEntryDTO("Test","ttesttest",List.of(Genre.Crime),LocalDate.now(),18,MediaType.Series));

        assertEquals("Test", result.getTitle());
        assertEquals(MediaType.Series, result.getMediaType());
        assertEquals(18, result.getAgeRestriction());
    }

    @Test
    public void createMedia_invalidAge_shouldThrow(){
        int expected = mediaRepo.findAll().size();
        assertThrows(InputValidationException.class,()-> service.createMediaEntry(user.getId(),new CreateMediaEntryDTO("Test","ttesttest",List.of(Genre.Crime),LocalDate.now(),-1,MediaType.Series)));
        int result = mediaRepo.findAll().size();

        assertEquals(expected,result);
    }

    @Test
    public void createRating_withValidData_shouldAddRatingToMedia(){
        when(mediaRepo.findById(entry.getId())).thenReturn(Optional.of(entry));

        Rating rating = service.createRating(user.getId(),entry.getId(),new CreateRatingDTO(5,"testComment"));

        assertEquals(rating.getOwnerId(),user.getId());
        assertEquals(rating.getMediaEntryId(),entry.getId());
        assertEquals(5, rating.getStars());
        assertEquals("testComment", rating.getComment());
    }
    @Test
    public void createRating_withValidDataWithoutComment_shouldAddRatingToMedia(){
        when(mediaRepo.findById(entry.getId())).thenReturn(Optional.of(entry));

        Rating rating = service.createRating(user.getId(),entry.getId(),new CreateRatingDTO(5,null));

        assertEquals(rating.getOwnerId(),user.getId());
        assertEquals(rating.getMediaEntryId(),entry.getId());
        assertEquals(5, rating.getStars());
        assertNull(rating.getComment());
    }

}
