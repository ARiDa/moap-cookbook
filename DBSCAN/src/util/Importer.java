package util;

import java.util.Iterator;

import arida.ufc.br.moap.datamodelapi.instances.api.IInstance;
import arida.ufc.br.moap.datamodelapi.instances.imp.Instance;
import arida.ufc.br.moap.datamodelapi.instances.imp.InstancesBasedModelImpl;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
/*
 * Importando os dados do banco de dados.
 * Os dados vem como instancias entao precisamos trata-los.
 * A importacao poderia ser feita tambem atraves de um arquivo csv.
 */
public class Importer {

	public static InstancesBasedModelImpl doImport() {

		PostgresqlProvider provider = new PostgresqlProvider("postgres",
				"postgres", "jdbc:postgresql://localhost/postgres");

		/*
		 * instanciando um modelo geral para trabalhar com os dados
		 */
		InstancesBasedModelImpl model = new InstancesBasedModelImpl();
		/*
		 * Fazendo uma consulta
		 */
		String query2 = "select * from pessoa";
		model = (InstancesBasedModelImpl) provider.retrieveInstances(query2,
				model);
		System.out.println("Quantidade de Atributos: "
				+ model.attributesCount());
		System.out.println("Primeiro atributo: " + model.getAttribute(1));
		System.out.println("Segundo atributo: " + model.getAttribute(2));

		/*
		 * Iterando sobre os valores
		 */
		for (Iterator<IInstance> it = model.getInstances().iterator(); it
				.hasNext();) {
			Instance o = (Instance) it.next();

			System.out.println(o.getValue(0));
			System.out.println(o.getValue(1));
			System.out.println(o.getValue(2));
		}

		return model;

	}

}
