package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.cart.Contact
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.ContactModel

class ContactEntityDataMapper: Mapper<Contact?, ContactModel>() {

    override fun mapFrom(from: Contact?): ContactModel {
        val data = ContactModel (
                id          = from?.id.toString(),
                firstName   = from?.firstName.toString(),
                lastName    = from?.lastName.toString(),
                mobilePhone = from?.mobilePhone.toString(),
                employeeId  = from?.employeeId.toString()
        )

        return data
    }
}