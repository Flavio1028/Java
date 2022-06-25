package br.com.optional;

import java.util.Optional;
import java.util.stream.Stream;

public class JavaOptional {

	public static void main(String[] args) {
		
		String valor = "2319";
		Optional<Integer> numero = converterEmNumero(valor);
		
		// Recupera o valor
		if(numero.isPresent()) {			
			System.out.println(numero.get());
		}
		
		// So executa se o valor estiver presente
		numero.ifPresent(n -> System.out.println(n));
		
		// Retorna o valor padrao caso nao tenha o primeiro valor
		System.out.println(numero.orElse(2));
		
		// Recebe uma funcao 
		Integer valorTeste = numero.orElseGet(() -> {
			return 5;
		});
		System.out.println(valorTeste);
		
		// Lanca uma excessao
		numero.orElseThrow(() -> new NullPointerException("Valor vazio."));
		
		
		Stream.of(1 ,2, 3)
			.findFirst()
				.ifPresent(System.out::print);
		
	}

	public static Optional<Integer> converterEmNumero(String numeroStr) {
		try {
			Integer integer = Integer.valueOf(numeroStr);
			return Optional.of(integer);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}