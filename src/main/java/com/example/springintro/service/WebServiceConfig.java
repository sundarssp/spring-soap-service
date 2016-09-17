package com.example.springintro.service;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(new PayloadLoggingInterceptor());
		interceptors.add(new SoapEnvelopeLoggingInterceptor());

		Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();
		security.setValidationActions("Signature");

		addSignatureValidation(security, interceptors);
		
		// Add securement
		security.setSecurementActions("Signature");
		
		addSignatureSecurement(security, interceptors);
		interceptors.add(security);
	}

	private void addSignatureSecurement(Wss4jSecurityInterceptor security, List<EndpointInterceptor> interceptors) {
		security.setSecurementUsername("myalias");
		security.setSecurementPassword("myaliasPassword");
		
		CryptoFactoryBean cfb = new CryptoFactoryBean();
		try {
			cfb.setKeyStoreLocation(new ClassPathResource("serverprivatestore.jks"));
			cfb.setKeyStorePassword("keyStorePassword");
			cfb.afterPropertiesSet();

			security.setSecurementSignatureCrypto(cfb.getObject());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addSignatureValidation(Wss4jSecurityInterceptor security, List<EndpointInterceptor> interceptors) {
		CryptoFactoryBean cfb = new CryptoFactoryBean();
		try {
			cfb.setKeyStoreLocation(new ClassPathResource("consumerpublicstore.jks"));
			cfb.setKeyStorePassword("keyStorePassword");
			cfb.afterPropertiesSet();

			security.setValidationSignatureCrypto(cfb.getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "population")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema populationSchema) {

		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PopulationPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://www.example.com/springintro/population");
		wsdl11Definition.setSchema(populationSchema);

		return wsdl11Definition;
	}

	@Bean
	public XsdSchema populationSchema() {
		return new SimpleXsdSchema(new ClassPathResource("population.xsd"));
	}

}