package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.exception.InvalideTogetException;


public interface Imetier <T,U>{
	
	public T creer(T entity) throws InvalideTogetException;
	
	public T modifier(T entity) throws InvalideTogetException;
	
	public List<T> findAll();
	
	public T findById(U id);

	public boolean supprimer(U id);
	
	public boolean supprimer(List<T> entites);
	
	public boolean existe(U id);
	

}
