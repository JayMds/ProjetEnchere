package fr.eni.encheres.dal;

import java.util.ArrayList;
import java.util.List;

public class DALException extends Exception {
	// Chaînage des Exceptions
	public DALException(String message) {
		super(message);
	}
	public DALException(Throwable cause ) {
		super(cause);
	}
	public Object getListeCodesErreur() {
		// TODO Auto-generated method stub
		return null;
	}

	}


