package ci.weget.web.config.firebase;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ci.weget.web.entites.personne.Personne;


@Configuration
public class WebConfig {
	public final static String MODEL_MAPPER = "ModelMapperWeb";

	@Bean(name = MODEL_MAPPER)
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(new Converter<Personne, Personne>() {

			public Personne convert(MappingContext<Personne, Personne> context) {
				Personne entity = context.getSource();
				Personne testJson = context.getDestination();
				//testJson.setOutId(entity.getId());
				testJson.setLogin(entity.getLogin());

				return testJson;
			}
		});

		return mapper;
	}
}
