package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorContatoResponseDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.FornecedorResponseDTO;
import br.com.eduardofbom.stock_manager_api.domain.model.Fornecedor;
import br.com.eduardofbom.stock_manager_api.domain.model.FornecedorContato;
import br.com.eduardofbom.stock_manager_api.domain.repository.IFornecedorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FornecedorService {

    private final IFornecedorRepository fornecedorRepository;

    public FornecedorService(IFornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public FornecedorResponseDTO criar(FornecedorRequestDTO fornecedorRequestDTO) {
        Fornecedor fornecedor = new Fornecedor(
                fornecedorRequestDTO.nome(),
                fornecedorRequestDTO.documentoFiscal()
        );

        if (fornecedorRequestDTO.contatos() != null) {
            fornecedorRequestDTO.contatos().forEach(
                    contatoDto -> {
                        FornecedorContato contato = new FornecedorContato(
                                contatoDto.tipo(),
                                contatoDto.valor(),
                                contatoDto.nomeContato()
                        );
                        fornecedor.adicionarContato(contato);
                    }
            );
        }
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return mapearParaResponseDTO(fornecedorSalvo);
    }

    private FornecedorResponseDTO mapearParaResponseDTO(Fornecedor fornecedor) {
        List<FornecedorContatoResponseDTO> contatosResponseDto = fornecedor.getContatos().stream()
                .map(contato -> new FornecedorContatoResponseDTO(
                        contato.getId(),
                        contato.getTipo(),
                        contato.getValor(),
                        contato.getNomeContato()
                )).toList();

        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getDocumentoFiscal(),
                fornecedor.isAtivo(),
                contatosResponseDto
        );
    }
}
