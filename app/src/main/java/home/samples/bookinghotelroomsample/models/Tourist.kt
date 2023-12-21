package home.samples.bookinghotelroomsample.models

open class RecyclerItem

data class Tourist(
    var informationHidden: Boolean?,
    var firstName: String?,
    var firstNameFieldStatus: Boolean?,
    var surname: String?,
    var surnameFieldStatus: Boolean?,
    var birthDate: String?,
    var birthDateFieldStatus: Boolean?,
    var citizenship: String?,
    var citizenshipFieldStatus: Boolean?,
    var passportNumber: String?,
    var passportNumberFieldStatus: Boolean?,
    var passportValidityPeriod: String?,
    var passportValidityPeriodFieldStatus: Boolean?
) : RecyclerItem()

class EmptyRecyclerItem : RecyclerItem()