package com.koudai.udc.persistence;

import com.koudai.udc.domain.PriceRemindAnonymous;

public interface PriceRemindAnonymousDAO {

	void save(PriceRemindAnonymous priceRemindAnonymous);

	PriceRemindAnonymous getPriceRemindAnonymousByEmailAndProductId(String email, String productId);

}
