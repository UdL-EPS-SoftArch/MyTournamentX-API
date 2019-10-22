package cat.udl.eps.softarch.mytournamentx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "The request is not valid")
public class BadRequestException extends RuntimeException {
}
