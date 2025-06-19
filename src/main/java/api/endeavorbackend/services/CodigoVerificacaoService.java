package api.endeavorbackend.services;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class CodigoVerificacaoService {

    private final Map<String, String> codigos = new ConcurrentHashMap<>();

    public String gerarCodigo(String email) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        codigos.put(email, codigo);
        return codigo;
    }

    public boolean verificarCodigo(String email, String codigoInformado) {
        return codigoInformado.equals(codigos.get(email));
    }

    public void removerCodigo(String email) {
        codigos.remove(email);
    }
}

