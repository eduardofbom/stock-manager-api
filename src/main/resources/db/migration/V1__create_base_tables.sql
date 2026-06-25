CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
)

CREATE TABLE fornecedores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    documento_fiscal VARCHAR(30)
)

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,  -- 'GERENTE', 'OPERADOR'
    ativo BOOLEAN DEFAULT TRUE
    -- Por enquanto sem login (email e senha)
)

CREATE TABLE fornecedor_contatos (
    id SERIAL PRIMARY KEY,
    fornecedor_id INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,       -- 'Telefone', 'E-mail', 'WhatsApp', 'Celular'
    valor VARCHAR(255) NOT NULL,     -- número ou e-mail
    nome_contato VARCHAR(100),         -- Nome da pessoa física (ex: 'Fulano do Financeiro')
    CONSTRAINT fk_fornecedor_contato FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
)

CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria_id INTEGER NOT NULL,
    cod_barras CHAR(13),
    unid_medida CHAR(2) NOT NULL,  -- 'KG' ou 'UN'
    estoq_min NUMERIC(10,3) NOT NULL,
    quant_atual NUMERIC(10,3) DEFAULT 0.000,  -- ATUALIZAR COM TRANSACTION (usar como cache)
    preco_venda NUMERIC(10,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
)

CREATE TABLE lotes (
    id SERIAL PRIMARY KEY,
    produto_id INTEGER NOT NULL,
    fornecedor_id INTEGER NOT NULL,
    lancamento TIMESTAMP  DEFAULT NOW(),
    quant_inicial NUMERIC(10,3) NOT NULL,  -- Imutável, registrada na entrada
    quant_disponivel NUMERIC(10,3) NOT NULL,  -- ariável, onde ocorre a baixa de estoque
    custo_unid NUMERIC(10,2) NOT NULL,
    validade DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVO',  -- 'ATIVO', 'ESGOTADO', 'VENCIDO', 'DESCARTADO'
    CONSTRAINT fk_lote_produto FOREIGN KEY (produto_id) REFERENCES produtos(id),
    CONSTRAINT fk_lote_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
)

CREATE TABLE movimentacoes_estoque (
    id SERIAL PRIMARY KEY,
    lote_id INTEGER NOT NULL,
    usuario_id INTEGER,
    data_movimentacao TIMESTAMP DEFAULT NOW(),
    quantidade NUMERIC(10,3) NOT NULL,   -- Positiva (entrada/ajuste) ou Negativa (venda/perda)
    tipo VARCHAR(30) NOT NULL,   -- 'VENDA', 'ENTRADA_COMPRA', 'DESCARTE_VENCIDO', 'AVARIA', 'AJUSTE_MANUAL'
    descricao TEXT,
    CONSTRAINT fk_movimentacao_lote FOREIGN KEY (lote_id) REFERENCES lotes(id),
    CONSTRAINT fk_movimentacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
)
