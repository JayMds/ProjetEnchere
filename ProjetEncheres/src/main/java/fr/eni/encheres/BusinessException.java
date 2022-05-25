package fr.eni.encheres;

import java.util.ArrayList;
import java.util.List;

	public class BusinessException extends Exception {
		private static final long serialVersionUID = 1L;
		private List<Integer> listeCodesErreur;
		
		public BusinessException() {
			super();
			this.listeCodesErreur=new ArrayList<>();
		}
	
}
