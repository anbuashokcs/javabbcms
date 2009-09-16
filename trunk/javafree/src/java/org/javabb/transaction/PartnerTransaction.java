package org.javabb.transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.javabb.dao.hibernate.HibernateDAO;
import org.javabb.vo.Partner;

public class PartnerTransaction extends Transaction {

	private static List<Partner> partners = null;
	private static HashMap<Long, String> emails = null;

	private HibernateDAO genericDAO;

	public void setGenericDAO(HibernateDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	@SuppressWarnings("unchecked")
	public List<Partner> findAllPartners() throws Exception {
		if (partners == null) {
			partners = genericDAO.loadAll(new Partner());
		}
		return partners;
	}

	public HashMap<Long, String> getEmails() throws Exception {
		if (emails == null) {
			List<Partner> part = findAllPartners();
			if (part != null) {
				for (Partner partner : part) {
					emails.put(partner.getPartnerId(), partner.getEmail());
				}
			}
		}
		return emails;
	}
	
	public Partner loadByPartnerId(Long partnerId) throws Exception{
		return (Partner) genericDAO.load(new Partner(), partnerId);
	}

}
