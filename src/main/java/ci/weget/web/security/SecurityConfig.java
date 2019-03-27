package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
		.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		////////// inscription et authentification ///////////////////////////////
				http.authorizeRequests()
						.antMatchers("/login/**", "/register/**", "/resendRegistrationToken/**", "/passwordOublier/**",
								"/newPassword/**", "/adminMembres/**", "/membres/**", "/abonnesSpeciaux",
								"/verifierLoginToken/**", "/regitrationConfirm/**")
						.permitAll();
				/////////////////////////////// membres ///////////////////////////////
				http.authorizeRequests()
						.antMatchers("/getByLogin/**", "/membre/**","/videoMembre/**","/modifPasswordCompte/**","/imageMembre/**", 
								"/getImageMembre/**","/imageCouvertureMembre/**", "/getImageCouvertureMembre/**")
						.permitAll();

				/////////////////////////////// admin ///////////////////////////////
				http.authorizeRequests().antMatchers("/admin/**","/adminSup/**","/adminParAdminSup/**","/adminModifPassword/**","/loginAdmin/**").permitAll();
			

		/////////////////////////////// les espaces ///////////////////////////////
		http.authorizeRequests()
				.antMatchers("/rechercheBlock/**","/getImagePublicite/**","/imagePub/**", "/categoryBlocks/**", "/tousLesAbonnesParBlock/**",
						"/tousLesBlockParAbonne/**", "/detailAbonnementParIdBlock/**", "/abonneParblocks/**",
						"/espace/**", "/photoBlock/**", "/getPhotoBlock/**", "/Personneblocks/**", "/tarifsBlocksId/**", "/ajouterDb/**")
				.permitAll();
		/////////////////////////////// Abonnement ///////////////////////////////
		http.authorizeRequests()
				.antMatchers("/galleryParIdDetailAbonnement/**", "/reabonneGratuit/**","/getAbonnementByIdPersonne/**",
						"/photoGalleryDetailAbonnement/**", "/abonnement/**","/getAbonnementByMois/**",
						 "/videoGalleryDetailAbonnement/**", "/updateVue/**","/getDistinctAbonnement/**",
						 "/profilAbonneLogin/**","/getAbonnementBani/**","/getAbonnementByParam/**",
						"/detailAbonnementLogo/**","/findParCompetence/**", "/cvAbonnement/**",
						"/https://api.cinetpay.com/v1/**", "/getAbonnementSpecial/**","/getAbonnementByLogin/**",
						"/getAbonnementByIdEspace/**","/tarifsAbonnementId/**", "/getAbonnementByVille/**" ,"/rechercheParComptence/**", "/rechercheParComptenceOuVille/**","/abonnementAll/**")
				.permitAll();
		/////////////////////////////// authorizeRequests ecole////////////////////////////
		/////////////////////////////// ///////////////////////////////
		http.authorizeRequests().antMatchers("/ecole/**", "/getEcoleByIdAbonnement/**",
				 "/uploadLogo/**", "/getLogoEcole/**","/chiffre/**",
					"/temoignage/**","/cursus/**","/infoEntete/",
				"/getDetailAbonnementVideo/**","/getEcoleByIdEspace/**","/getEcoleByMc/**","/getEcoleByIdEtablissement/**",
				"/uploadPhotoCouverture/**", "/getEcolePhotoCouverture/**").permitAll();
		////////////////////////////////////////////////////////////////////////////////////////
		http.authorizeRequests().antMatchers("/quartier/**", "/quartierParVille/**", "/abonneSpecial/**").permitAll();

		/////////////////////////////// les combos ///////////////////////////////

		http.authorizeRequests()
				.antMatchers("/specialite/**", "/niveauEtude/**", "/disponibilite/**", "/langue/**","/comboTypeEtablissement/**",
						"/contratPeriode/**", "/emploieType/**", "/fonction/**", "/comboExperience/**",
						"/contratType/**", "/domaine/**", "/comboTarif/**", "/fonction/**", "/api/client/**")
				.permitAll();
		///////////////////////////////////////////////////////////////////////
		http.authorizeRequests().antMatchers("/partenaireLogo/**", "/getLogoPartenaire/**", "/temoignagePhoto/**",
				"/getPhotoTemoignage/**").permitAll();
		/////////////////////////// adresse/////////////////////////////////////////
		http.authorizeRequests().antMatchers("/pays/**", "/ville/**", "/villeParPays/**").permitAll();
		//////////////////////////////////////////////////////////////////////////////////////////////
		http.authorizeRequests().antMatchers("/photoCouvertureMembre/**", "/paiement/**", "/cvPersonne/{id}/**",
				"/cvPersonne/{id}/cursus/**").permitAll();

		/////////////////////////////// ///////////////////////////////

		http.authorizeRequests().antMatchers("/personnesparId/**").permitAll();
		http.authorizeRequests().antMatchers().permitAll();
		http.authorizeRequests().antMatchers("/documentCompetence/**","/ajouterUR/**", "/fichierCv/**").permitAll();

		http.authorizeRequests().antMatchers("/blocks/**", "/membreStatut/**", "/misAjourProfil/**",
				"/abonneGratuit/**", "/abonnement/**", "/statutAbonne/**", "/modifierMdp/**").permitAll();
		http.authorizeRequests().antMatchers("/diplome/**", "/infoEntete/**").permitAll();
		http.authorizeRequests().antMatchers("/publiciteIdPosition/**", "/imagePub/**", "/getImagePublicite/**",
				"/videoPub/**", "/getVideoPublicite/**").permitAll();
		http.authorizeRequests().antMatchers("/detailAbonnementParEtablissement/**", "/typeEtablissement/**")
				.permitAll();
		http.authorizeRequests().antMatchers("/roleParPersonne/**").permitAll();

		http.authorizeRequests().antMatchers("/misAjourProfil/**", "/cvPersonne/**", "/cvPersonneParIdAbonnement/**",
				"/publicite/**", "/experience/**", "/cursus/**", "/experienceParCv/**", "/cursusParCv/**").permitAll();

		/////////////////////////////// gallery ///////////////////////////////
		http.authorizeRequests().antMatchers("/gallery/**", "/photoGallery/**", "/photoGalleryAbonnement/**",
				"/getAbonnementGallery/**","/getDetailAbonnementGallery/**",
				"/galleryParIdAbonnement/**","/galleryImageDetailAbonnement/**","/galleryImageAbonnement/**",
				"/videoAbonnement/**","/getAbonnementVideo/**").permitAll();

		/////////////////////////////// Role ///////////////////////////////
		http.authorizeRequests().antMatchers("/role/**").permitAll();
       
        /////////////////////////////// experience ///////////////////////////////
		http.authorizeRequests().antMatchers("/experience/**","/membresFirebase/**").permitAll();

        /////////////////////////////// commande ///////////////////////////////
		http.authorizeRequests().antMatchers("/commande/**").permitAll();
        /////////////////////////////// partenaire ///////////////////////////////
		http.authorizeRequests().antMatchers("/partenaire/**","/partenaireToget/**",
				"/photoPartenaireToget/**","/getPhotoPartenaireToget/**").permitAll();

        /////////////////////////////// paiement ///////////////////////////////
		http.authorizeRequests().antMatchers("/paiement/**","/paiementModif/**","/reponseCinetPay/**").permitAll();

        /////////////////////////////// Cv personne ///////////////////////////////
		http.authorizeRequests().antMatchers("/getCvPersonneByIdAbonnemnt/**","/cvPersonne/**").permitAll();

         /////////////////////////////// doc competence ///////////////////////////////
		http.authorizeRequests()
				.antMatchers("/photoDocumentCompetence/**", "/getDocCompetence/**", "/documentCompetence/**")
				.permitAll();

        /////////////////////////////// document ///////////////////////////////
		http.authorizeRequests().antMatchers("/documentParIdDetailAbonnement/**", "/document/**",
				"/detailAbonnementDocument/**", "/getDetailAbonnementDocument/**").permitAll();

        /////////////////////////////// flash info ///////////////////////////////
		http.authorizeRequests().antMatchers("/flashInfo/**", "/flashInfoDetailAbonnement/**").permitAll();

        /////////////////////////////// formation ///////////////////////////////
		http.authorizeRequests()
				.antMatchers("/formationCatalogue/**", "/getCatalogueFormation/**", "/formations/**",
						"/formationPhoto/**", "/formationDetailAbonnement/**", "/getPhotoFormation/**",
						"/formationFormulaire/**", "/getFormulaireFormation/**")
				.permitAll();

        /////////////////////////////// info entete ///////////////////////////////
		http.authorizeRequests().antMatchers("/messageries/**").permitAll();

        /////////////////////////////// messagerie ///////////////////////////////
		http.authorizeRequests().antMatchers("/messageries/**", 
				"/sendMessage/**", "/transferMessage/**","/updateStatutMessage/**",
				"/getMessageByPersonne/**",
				"/getMessageById/**")
				.permitAll();

        /////////////////////////////// tarif ///////////////////////////////
		http.authorizeRequests().antMatchers("/tarif/**","/getTarifByIdEspace/**","/getTarifByIdAbonnemnt/**").permitAll();

        /////////////////////////////// type etablissement  ///////////////////////////////
		http.authorizeRequests().antMatchers("/messageries/**","/photoTypeEtablissement/**","/getPhotoTypeEtablissement/**").permitAll();

        /////////////////////////////// panier  ///////////////////////////////
		http.authorizeRequests().antMatchers("/panier/**", "/panierParPersonne/**").permitAll();
        /////////////////////////////// faq  ///////////////////////////////
		http.authorizeRequests().antMatchers("/categorieFaq/**", "/questionnaireFaq/**",
				"/reponseFaq/**", "/questionFaqParCat/**","/reponseFaqParQues/**","/videoTuto/**").permitAll();
		 /////////////////////////////// role  ///////////////////////////////
		http.authorizeRequests().antMatchers("/role/**").permitAll();
		// toutes les requetes necessite une authentication
		http.authorizeRequests().anyRequest().authenticated();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
