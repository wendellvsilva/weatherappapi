package api.openweather.weatherapp.exceptions;

public class ClimaNotFoundException extends RuntimeException {

    public ClimaNotFoundException() {
        super("Clima não encontrado");
    }

    public ClimaNotFoundException(String message) {
        super(message);
    }
}
