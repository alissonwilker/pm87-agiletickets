package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest  {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void deveCriarUmaSessaoParaDatasIguaisPeriodicidadeDiaria() {
		Espetaculo ivete = new Espetaculo();

		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate();
		LocalTime horario = new LocalTime();
		
		List<Sessao> sessoes = ivete.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertEquals(1, sessoes.size());
		Sessao sessao = sessoes.get(0);
		assertEquals(ivete, sessao.getEspetaculo());
		
		LocalDate inicioSessao = sessao.getInicio().toLocalDate();
		assertEquals(inicio, inicioSessao);

		LocalTime horarioSessao = sessao.getInicio().toLocalTime();
		assertEquals(horario, horarioSessao);

	}

	@Test
	public void deveCriarMaisSessoesParaDatasDistintasPeriodicidadeDiaria() {
		Espetaculo ivete = new Espetaculo();

		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusDays(2);
		LocalTime horario = new LocalTime();
		
		List<Sessao> sessoes = ivete.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertEquals(3, sessoes.size());
		Sessao sessao = sessoes.get(2);
		assertEquals(ivete, sessao.getEspetaculo());
		
		LocalDate inicioSessao = sessao.getInicio().toLocalDate();
		assertEquals(inicio.plusDays(2), inicioSessao);

		LocalTime horarioSessao = sessao.getInicio().toLocalTime();
		assertEquals(horario, horarioSessao);

	}

	@Test
	public void deveCriarMaisSessoesParaPeriodoMaiorQueUmaSemanaPeriodicidadeSemanal() {
		Espetaculo ivete = new Espetaculo();

		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusDays(20);
		LocalTime horario = new LocalTime();
		
		List<Sessao> sessoes = ivete.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		assertEquals(3, sessoes.size());
		Sessao sessao = sessoes.get(2);
		assertEquals(ivete, sessao.getEspetaculo());
		
		LocalDate inicioSessao = sessao.getInicio().toLocalDate();
		//o inicio da ultima sessao sera em 20 dias menos a quantidade de dias que sobram antes de completar mais uma semana.
		assertEquals(inicio.plusDays(20 - (20%7)), inicioSessao);

		LocalTime horarioSessao = sessao.getInicio().toLocalTime();
		assertEquals(horario, horarioSessao);

	}
	
	@Test
	public void deveCriarUmaSessaoParaPeriodoMenorQueUmaSemanaPeriodicidadeSemanal() {
		Espetaculo ivete = new Espetaculo();

		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusDays(6);
		LocalTime horario = new LocalTime();
		
		List<Sessao> sessoes = ivete.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		assertEquals(1, sessoes.size());
		Sessao sessao = sessoes.get(0);
		assertEquals(ivete, sessao.getEspetaculo());
		
		LocalDate inicioSessao = sessao.getInicio().toLocalDate();
		assertEquals(inicio, inicioSessao);

		LocalTime horarioSessao = sessao.getInicio().toLocalTime();
		assertEquals(horario, horarioSessao);

	}
}
