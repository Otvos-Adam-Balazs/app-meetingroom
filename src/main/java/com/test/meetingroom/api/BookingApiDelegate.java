package com.test.meetingroom.api;

import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.model.CreateBooking400Response;
import com.test.meetingroom.model.ErrorResponse;
import com.test.meetingroom.model.ValidationError;
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
     * POST /api/bookings/add : Create a new booking
     *
     * @param bookingRequest  (required)
     * @return The created booking (status code 201)
     *         or Invalid input or validation error (status code 400)
     *         or Booking conflict (status code 409)
     * @see BookingApi#createBooking
     */
    default ResponseEntity<BookingDto> createBooking(BookingRequest bookingRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6, \"roomName\" : \"roomName\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /api/bookings/{id} : Delete a booking
     *
     * @param id  (required)
     * @return Successfully deleted (status code 200)
     *         or Booking not found (status code 404)
     * @see BookingApi#deleteBooking
     */
    default ResponseEntity<Void> deleteBooking(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/admin/allBooking : List bookings
     *
     * @return List of all bookings (status code 200)
     * @see BookingApi#getBookings
     */
    default ResponseEntity<List<BookingDto>> getBookings() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6, \"roomName\" : \"roomName\" }, { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6, \"roomName\" : \"roomName\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /api/bookings : List bookings by meeting room
     *
     * @param roomId Filter by meeting room (required)
     * @return List of bookings (status code 200)
     *         or Validation error (status code 400)
     * @see BookingApi#getBookingsByMeetingRoom
     */
    default ResponseEntity<List<BookingDto>> getBookingsByMeetingRoom(Integer roomId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6, \"roomName\" : \"roomName\" }, { \"startTime\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : 0, \"endTime\" : \"2000-01-23T04:56:07.000+00:00\", \"roomId\" : 6, \"roomName\" : \"roomName\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
