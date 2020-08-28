package opsigo.com.datalayer.mapper

import opsigo.com.datalayer.model.Contact
import opsigo.com.domainlayer.Mapper
import opsigo.com.domainlayer.model.summary.ContactModel

class ContactEntityDataMapper
constructor() : Mapper<Contact, ContactModel>() {

    override fun mapFrom(from: Contact): ContactModel {
        val data = ContactModel (
                id          = from.id,
                firstName   = from.firstName,
                lastName    = from.lastName,
                mobilePhone = from.mobilePhone,
                employeeId  = from.employeeId
        )

        return data
    }
}