package br.com.caelum.agiletickets.models;

public enum Periodicidade {
	
	DIARIA {
		public CriadorDeSessoes getCriadorDeSessoes() {
			return new CriadorDeSessoesDiarias();
		}
	}, SEMANAL {
		public CriadorDeSessoes getCriadorDeSessoes() {
			return new CriadorDeSessoesSemanais();
		}
	};
	
	public abstract CriadorDeSessoes getCriadorDeSessoes();
}
