package com.test.meetingroom.api;

import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link BookingApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface BookingApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/admin/bookings : Listázza a foglalásokat
     *
     * @param roomId Szűrés tárgyaló szerint (required)
     * @return Foglalások listája (status code 200)
     * @see BookingApi#bookings
     */
    default ResponseEntity<List<BookingDto>> bookings(Integer roomId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6 }, { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /api/bookings/add : Új foglalás létrehozása
     *
     * @param bookingRequest  (required)
     * @return A létrehozott foglalás (status code 200)
     * @see BookingApi#createBooking
     */
    default ResponseEntity<BookingDto> createBooking(BookingRequest bookingRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /api/bookings/{id} : Foglalás törlése
     *
     * @param id  (required)
     * @return Sikeres törlés (status code 204)
     * @see BookingApi#deleteBooking
     */
    default ResponseEntity<Void> deleteBooking(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
