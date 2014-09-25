package de.lsn.example.fscrud.service;

public interface CrudService<T> {

	public T loadAll(Class<T> classOfT);
	
	public T load(Class<T> classOfT, Long id);
	
	public T save(T t);
	
}
