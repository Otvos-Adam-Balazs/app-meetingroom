package com.test.meetingroom.api;

import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.model.CreateBooking400Response;
import com.test.meetingroom.model.ErrorResponse;
import com.test.meetingroom.model.ValidationError;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.meetingRoom.base-path:}")
public class BookingApiController implements BookingApi {

    private final BookingApiDelegate delegate;

    public BookingApiController(@Autowired(required = false) BookingApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new BookingApiDelegate() {});
    }

    @Override
    public BookingApiDelegate getDelegate() {
        return delegate;
    }

}
