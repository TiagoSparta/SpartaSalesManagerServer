package br.com.Sparta.SpartaSalesManager.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
public class EndpointUtil<T> implements Serializable {
    public ResponseEntity<?> returnObjectOrNotFound(Optional<T> t) {
        return !t.isPresent() ? ResponseEntity.notFound().build() : ResponseEntity.status(OK).body(t);
    }

    public ResponseEntity<?> returnObjectOrNotFound(List<Optional<T>> t) {
        return t.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(OK).body(t);
    }
}
