package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;

public class CalculadoraDePrecos {
	
	private static final int SESSENTA_MINUTOS = 60;
	private static final double THRESHOLD_ULTIMOS_INGRESSOS_CINEMA_SHOW = 0.05;
	private static final double THRESHOLD_ULTIMOS_INGRESSOS_BALLET_ORQUESTRA = 0.50;
	private static final double TAXA_EXTRA_ULTIMOS_INGRESSOS_CINEMA_SHOW = 0.10;
	private static final double TAXA_EXTRA_ULTIMOS_INGRESSOS_BALLET_ORQUESTRA = 0.20;
	private static final double TAXA_EXTRA_BALLET_ORQUESTRA_LONGA= 0.10;

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();

		switch (sessao.getEspetaculo().getTipo()) {
		case CINEMA:
		case SHOW:
			if(ultimosIngressos(sessao, THRESHOLD_ULTIMOS_INGRESSOS_CINEMA_SHOW)) { 
				preco = acrescentarTaxaExtra(sessao, preco, TAXA_EXTRA_ULTIMOS_INGRESSOS_CINEMA_SHOW);
			}
			
			break;
		case BALLET:
		case ORQUESTRA:
			if(ultimosIngressos(sessao, THRESHOLD_ULTIMOS_INGRESSOS_BALLET_ORQUESTRA)) { 
				preco = acrescentarTaxaExtra(sessao, preco, TAXA_EXTRA_ULTIMOS_INGRESSOS_BALLET_ORQUESTRA);
			}
			
			if(sessaoDuraMaisDeHora(sessao)){
				preco = acrescentarTaxaExtra(sessao, preco, TAXA_EXTRA_BALLET_ORQUESTRA_LONGA);
			}
			
			break;
		}
		
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	public static boolean ultimosIngressos(Sessao sessao, double threshold) {
		return calcularPorcentagemIngressosDisponiveis(sessao) <= threshold;
	}

	private static boolean sessaoDuraMaisDeHora(Sessao sessao) {
		return sessao.getDuracaoEmMinutos() > SESSENTA_MINUTOS;
	}

	private static BigDecimal acrescentarTaxaExtra(Sessao sessao, BigDecimal preco, double taxaExtra) {
		return preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(taxaExtra)));
	}

	private static double calcularPorcentagemIngressosDisponiveis(Sessao sessao) {
		Integer totalIngressosSessao = sessao.getTotalIngressos();
		return (totalIngressosSessao - sessao.getIngressosReservados()) / totalIngressosSessao.doubleValue();
	}

}