package api.openweather.weatherapp.exceptions;

public class CidadeNotFoundException extends RuntimeException {

    public CidadeNotFoundException() {
        super("Cidade não encontrada");
    }

    public CidadeNotFoundException(String message) {
        super(message);
    }
}
