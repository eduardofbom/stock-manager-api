package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorContatoRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorContatoResponseDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorResponseDTO;
import br.com.eduardofbom.stock_manager_api.domain.model.Fornecedor;
import br.com.eduardofbom.stock_manager_api.domain.model.FornecedorContato;
import br.com.eduardofbom.stock_manager_api.domain.model.TiposContato;
import br.com.eduardofbom.stock_manager_api.domain.repository.IFornecedorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FornecedorServiceTest {

    @Mock
    private IFornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    @DisplayName("Deve criar um fornecedor com múltiplos contatos com sucesso")
    void deveCriarFornecedorComContatosComSucesso() {
        List<FornecedorContatoRequestDTO> contatosRequestDTO = List.of(
                new FornecedorContatoRequestDTO(TiposContato.EMAIL,
                        "vendas@distribuidora.com", "Carlos Vendas"),
                new FornecedorContatoRequestDTO(TiposContato.CELULAR,
                        "79988776655","Suporte Logístico")
        );

        FornecedorRequestDTO fornecedorRequestDTO = new FornecedorRequestDTO(
                "Distribuidora de Bebidas X",
                "12.345.678/0001-09",
                contatosRequestDTO
        );

        Fornecedor fornecedorSalvoFake = new Fornecedor(
                "Distribuidora de Bebidas X",
                "12.345.678/0001-09"
        );
        injetarId(fornecedorSalvoFake, 100L);

        FornecedorContato contato1 = new FornecedorContato(
                TiposContato.EMAIL,
                "vendas@distribuidora.com",
                "Carlos Vendas"
        );
        injetarId(contato1, 1L);

        FornecedorContato contato2 = new FornecedorContato(
                TiposContato.CELULAR,
                "79988776655",
                "Suporte Logístico"
        );
        injetarId(contato2, 2L);

        fornecedorSalvoFake.adicionarContato(contato1);
        fornecedorSalvoFake.adicionarContato(contato2);

        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedorSalvoFake);

        FornecedorResponseDTO responseDTO = fornecedorService.criar(fornecedorRequestDTO);

        assertNotNull(responseDTO, "O response não pode ser nulo");
        assertEquals(100L, responseDTO.id());
        assertEquals("Distribuidora de Bebidas X", responseDTO.nome());
        assertEquals("12.345.678/0001-09",responseDTO.documentoFiscal());
        assertTrue(responseDTO.ativo(), "O fornecedor deve nascer ativo por padrão");

        assertNotNull(responseDTO.contatos());
        assertEquals(2, responseDTO.contatos().size(), "O fornecedor deve ter exatamente 2 contatos");

        FornecedorContatoResponseDTO primeiroContato = responseDTO.contatos().getFirst();
        assertEquals(1L, primeiroContato.id());
        assertEquals(TiposContato.EMAIL, primeiroContato.tipo());
        assertEquals("vendas@distribuidora.com", primeiroContato.valor());
        assertEquals("Carlos Vendas",primeiroContato.nomeContato());

        FornecedorContatoResponseDTO segundoContato = responseDTO.contatos().get(1);
        assertEquals(2L, segundoContato.id());
        assertEquals(TiposContato.CELULAR, segundoContato.tipo());
        assertEquals("79988776655", segundoContato.valor());
        assertEquals("Suporte Logístico",segundoContato.nomeContato());

        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    private void injetarId(Object objeto, Long id) {
        try {
            Field idField = objeto.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(objeto, id);
        } catch (Exception e) {
            fail("Falha ao injetar ID de teste via reflexão na classe " + objeto.getClass().getSimpleName());
        }
    }
}
