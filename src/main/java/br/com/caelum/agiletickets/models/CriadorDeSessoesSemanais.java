package br.com.caelum.agiletickets.models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

public class CriadorDeSessoesSemanais implements CriadorDeSessoes {
	public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim,
			LocalTime horario, Periodicidade periodicidade,
			Espetaculo espetaculo) {
		List<Sessao> sessoes = new ArrayList<Sessao>();
		for (int i = 0; i <= Weeks.weeksBetween(inicio, fim).getWeeks(); i++) {
			Sessao sessao = new Sessao();
			sessao.setEspetaculo(espetaculo);
			sessao.setInicio(inicio.plusWeeks(i).toDateTime(horario));
			sessoes.add(sessao);
		}

		return sessoes;

	}
}
