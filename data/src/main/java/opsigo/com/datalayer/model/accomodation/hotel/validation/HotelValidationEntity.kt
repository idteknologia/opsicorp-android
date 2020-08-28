package opsigo.com.datalayer.model.accomodation.hotel.validation

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class HotelValidationEntity(

        @field:SerializedName("RemarkHotel")
	val remarkHotel: String? = "",

        @field:SerializedName("Address")
	val address: String? = "",

        @field:SerializedName("IsSync")
	val isSync: Boolean = false,

        @field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

        @field:SerializedName("Hash")
	val hash: String? = "",

        @field:SerializedName("Image")
	val image: String? = "",

        @field:SerializedName("CancellationPolicies")
	val cancellationPolicies: List<Any?>? = null,

        @field:SerializedName("ActionDate")
	val actionDate: String? = "",

        @field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean = false,

        @field:SerializedName("Checkout")
	val checkout: String? = "",

        @field:SerializedName("RoomSelector")
	val roomSelector: String? = "",

        @field:SerializedName("Currency")
	val currency: String? = "",

        @field:SerializedName("RoomService")
	val roomService: String? = "",

        @field:SerializedName("CauseViolatedRules")
	val causeViolatedRules: String? = "",

        @field:SerializedName("CorrelationId")
	val correlationId: String? = "",

        @field:SerializedName("EmpId")
	val empId: String? = "",

        @field:SerializedName("Status")
	val status: Int? = null,

        @field:SerializedName("CheckinFormatString")
	val checkinFormatString: String? = "",

        @field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean = false,

        @field:SerializedName("TicketedDate")
	val ticketedDate: String? = "",

        @field:SerializedName("StatusName")
	val statusName: String? = "",

        @field:SerializedName("DiscountAmount")
	val discountAmount: Double = 0.0,

        @field:SerializedName("CheckinView")
	val checkinView: String? = "",

        @field:SerializedName("TripCode")
	val tripCode: String? = "",

        @field:SerializedName("TimeLimitView")
	val timeLimitView: String? = "",

        @field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean = false,

        @field:SerializedName("Country")
	val country: String? = "",

        @field:SerializedName("Id")
	val id: String? = "",

        @field:SerializedName("BookingNumber")
	val bookingNumber: String? = "",

        @field:SerializedName("BookingCode")
	val bookingCode: String? = "",

        @field:SerializedName("BookedDate")
	val bookedDate: String? = "",

        @field:SerializedName("TourismTax")
	val tourismTax: Double = 0.0,

        @field:SerializedName("HotelType")
	val hotelType: Int? = null,

        @field:SerializedName("CostId")
	val costId: String? = "",

        @field:SerializedName("RoomType")
	val roomType: String? = "",

        @field:SerializedName("LinkReference")
	val linkReference: String? = "",

        @field:SerializedName("CancellationApi")
	val cancellationApi: String? = "",

        @field:SerializedName("TimeLimit")
	val timeLimit: String? = "",

        @field:SerializedName("MapUri")
	val mapUri: String? = "",

        @field:SerializedName("ReffId")
	val reffId: String? = "",

        @field:SerializedName("Amount")
	val amount: Double = 0.0,

        @field:SerializedName("TotalAmountApi")
	val totalAmountApi: Double = 0.0,

        @field:SerializedName("Facilities")
	val facilities: String? = "",

        @field:SerializedName("Rooms")
	val rooms: Int? = null,

        @field:SerializedName("AmountWithSf")
	val amountWithSf: Double = 0.0,

        @field:SerializedName("CauseViolatedRulesParagraf")
	val causeViolatedRulesParagraf: String? = "",

        @field:SerializedName("TimeLimitTripPlan")
	val timeLimitTripPlan: String? = "",

        @field:SerializedName("DiscountPercentage")
	val discountPercentage: Int? = null,

        @field:SerializedName("Checkin")
	val checkin: String? = "",

        @field:SerializedName("TripPlanId")
	val tripPlanId: String? = "",

        @field:SerializedName("Email")
	val email: String? = "",

        @field:SerializedName("IsForwardedBooking")
	val isForwardedBooking: Boolean = false,

        @field:SerializedName("FormBookingUrl")
	val formBookingUrl: String? = "",

        @field:SerializedName("CancelReff")
	val cancelReff: String? = "",

        @field:SerializedName("Name")
	val name: String? = "",

        @field:SerializedName("Remarks")
	val remarks: List<Any?>? = null,

        @field:SerializedName("ContractNo")
	val contractNo: String? = "",

        @field:SerializedName("BookingId")
	val bookingId: String? = "",

        @field:SerializedName("TimeLimitString")
	val timeLimitString: String? = "",

        @field:SerializedName("ResponseBookUrl")
	val responseBookUrl: String? = "",

        @field:SerializedName("PaymentStatus")
	val paymentStatus: String? = "",

        @field:SerializedName("PaymentSuccess")
	val paymentSuccess: Boolean = false,

        @field:SerializedName("IsSentEmail")
	val isSentEmail: Boolean = false,

        @field:SerializedName("PaymentReff")
	val paymentReff: String? = "",

        @field:SerializedName("ConfirmationId")
	val confirmationId: String? = "",

        @field:SerializedName("CitiNameApi")
	val citiNameApi: String? = "",

        @field:SerializedName("Guest")
	val guest: List<Any?>? = null,

        @field:SerializedName("ConfirmationUrl")
	val confirmationUrl: String? = "",

        @field:SerializedName("Duration")
	val duration: Int? = null,

        @field:SerializedName("City")
	val city: String? = "",

        @field:SerializedName("CityName")
	val cityName: String? = "",

        @field:SerializedName("EmailFD")
	val emailFD: String? = "",

        @field:SerializedName("ServiceFee")
	val serviceFee: Double = 0.0,

        @field:SerializedName("Area")
	val area: String? = "",

        @field:SerializedName("IsFullDeduct")
	val isFullDeduct: Boolean = false,

        @field:SerializedName("DiscountType")
	val discountType: Int? = null,

        @field:SerializedName("PlafondAmount")
	val plafondAmount: Double = 0.0,

        @field:SerializedName("TravelAgent")
	val travelAgent: String? = "",

        @field:SerializedName("CreatedDate")
	val createdDate: String? = "",

        @field:SerializedName("Message")
	val message: String? = "",

        @field:SerializedName("Rating")
	val rating: Int? = null,

        @field:SerializedName("IsTourism")
	val isTourism: Boolean = false,

        @field:SerializedName("CancellationPoliciesView")
	val cancellationPoliciesView: String? = "",

        @field:SerializedName("CheckoutView")
	val checkoutView: String? = "",

        @field:SerializedName("EmailFO")
	val emailFO: String? = "",

        @field:SerializedName("PnrId")
	val pnrId: String? = "",

        @field:SerializedName("Phone")
	val phone: String? = "",

        @field:SerializedName("IsGuarantedBooking")
	val isGuarantedBooking: Boolean = false,

        @field:SerializedName("RemarkReject")
	val remarkReject: String? = "",

        @field:SerializedName("IsHsre")
	val isHsre: Boolean = false,

        @field:SerializedName("CheckOutFormatString")
	val checkOutFormatString: String? = "",

        @field:SerializedName("TotalAmount")
	val totalAmount: Double = 0.0,

        @field:SerializedName("IsWithBreakfast")
	val isWithBreakfast: Boolean = false,

        @field:SerializedName("BookingCodeNew")
	val bookingCodeNew: String? = "",

        @field:SerializedName("Contact")
	val contact: ContactHotelEntity = ContactHotelEntity(),

        @field:SerializedName("HotelKey")
	val hotelKey: String? = "",

        @field:SerializedName("RoomCategory")
	val roomCategory: String? = "",

        @field:SerializedName("TimeLimitFormatString")
	val timeLimitFormatString: String? = "",

        @field:SerializedName("TripItemId")
	val tripItemId: String? = "",

        @field:SerializedName("AmountPerNight")
	val amountPerNight: Double = 0.0
)