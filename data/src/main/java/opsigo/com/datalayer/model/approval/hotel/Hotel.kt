package opsigo.com.datalayer.model.approval.hotel

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import opsigo.com.datalayer.model.Contact
import opsigo.com.datalayer.model.PaymentsItem
import opsigo.com.datalayer.model.RemarksItem
import opsigo.com.datalayer.model.approval.hotel.GuestItem

@Generated("com.robohorse.robopojogenerator")
data class Hotel(

        @field:SerializedName("TripPlanId")
	val tripPlanId: String? = null,

        @field:SerializedName("Email")
	val email: String? = null,

        @field:SerializedName("RemarkHotel")
	val remarkHotel: Any? = null,

        @field:SerializedName("Address")
	val address: String? = null,

        @field:SerializedName("IsForwardedBooking")
	val isForwardedBooking: Boolean? = null,

        @field:SerializedName("IsSync")
	val isSync: Boolean? = null,

        @field:SerializedName("Payments")
	val payments: List<PaymentsItem?>? = null,

        @field:SerializedName("Hash")
	val hash: Any? = null,

        @field:SerializedName("Image")
	val image: String? = null,

        @field:SerializedName("CancellationPolicies")
	val cancellationPolicies: List<Any?>? = null,

        @field:SerializedName("FormBookingUrl")
	val formBookingUrl: String? = null,

        @field:SerializedName("CancelReff")
	val cancelReff: Any? = null,

        @field:SerializedName("Name")
	val name: String? = null,

        @field:SerializedName("ActionDate")
	val actionDate: String? = null,

        @field:SerializedName("IsFullCharge")
	val isFullCharge: Boolean? = null,

        @field:SerializedName("Checkout")
	val checkout: String? = null,

        @field:SerializedName("Remarks")
	val remarks: List<RemarksItem?>? = null,

        @field:SerializedName("ContractNo")
	val contractNo: Any? = null,

        @field:SerializedName("RoomSelector")
	val roomSelector: String? = null,

        @field:SerializedName("TimeLimitString")
	val timeLimitString: String? = null,

        @field:SerializedName("ResponseBookUrl")
	val responseBookUrl: Any? = null,

        @field:SerializedName("RoomService")
	val roomService: String? = null,

        @field:SerializedName("CauseViolatedRules")
	val causeViolatedRules: Any? = null,

        @field:SerializedName("CorrelationId")
	val correlationId: String? = null,

        @field:SerializedName("EmpId")
	val empId: Any? = null,

        @field:SerializedName("IsSentEmail")
	val isSentEmail: Boolean? = null,

        @field:SerializedName("Status")
	val status: Int? = null,

        @field:SerializedName("CheckinFormatString")
	val checkinFormatString: String? = null,

        @field:SerializedName("ConfirmationId")
	val confirmationId: String? = null,

        @field:SerializedName("IsEmailSent")
	val isEmailSent: Boolean? = null,

        @field:SerializedName("TicketedDate")
	val ticketedDate: Any? = null,

        @field:SerializedName("CitiNameApi")
	val citiNameApi: String? = null,

        @field:SerializedName("Guest")
	val guest: List<GuestItem?>? = null,

        @field:SerializedName("ConfirmationUrl")
	val confirmationUrl: Any? = null,

        @field:SerializedName("Duration")
	val duration: Int? = null,

        @field:SerializedName("StatusName")
	val statusName: String? = null,

        @field:SerializedName("City")
	val city: Any? = null,

        @field:SerializedName("CityName")
	val cityName: Any? = null,

        @field:SerializedName("EmailFD")
	val emailFD: Any? = null,

        @field:SerializedName("DiscountAmount")
	val discountAmount: Double? = null,

        @field:SerializedName("CheckinView")
	val checkinView: String? = null,

        @field:SerializedName("ServiceFee")
	val serviceFee: Double? = null,

        @field:SerializedName("Area")
	val area: String? = null,

        @field:SerializedName("TripCode")
	val tripCode: Any? = null,

        @field:SerializedName("TimeLimitView")
	val timeLimitView: String? = null,

        @field:SerializedName("IsFullDeduct")
	val isFullDeduct: Boolean? = null,

        @field:SerializedName("IsViolatedHotelRules")
	val isViolatedHotelRules: Boolean? = null,

        @field:SerializedName("DiscountType")
	val discountType: Int? = null,

        @field:SerializedName("PlafondAmount")
	val plafondAmount: Double? = null,

        @field:SerializedName("TravelAgent")
	val travelAgent: Any? = null,

        @field:SerializedName("CreatedDate")
	val createdDate: String? = null,

        @field:SerializedName("Country")
	val country: String? = null,

        @field:SerializedName("Id")
	val id: String? = null,

        @field:SerializedName("BookingNumber")
	val bookingNumber: Any? = null,

        @field:SerializedName("BookingCode")
	val bookingCode: String? = null,

        @field:SerializedName("Message")
	val message: Any? = null,

        @field:SerializedName("BookedDate")
	val bookedDate: String? = null,

        @field:SerializedName("Rating")
	val rating: Int? = null,

        @field:SerializedName("IsTourism")
	val isTourism: Boolean? = null,

        @field:SerializedName("TourismTax")
	val tourismTax: Double? = null,

        @field:SerializedName("HotelType")
	val hotelType: Int? = null,

        @field:SerializedName("CancellationPoliciesView")
	val cancellationPoliciesView: List<String?>? = null,

        @field:SerializedName("CostId")
	val costId: Any? = null,

        @field:SerializedName("CheckoutView")
	val checkoutView: String? = null,

        @field:SerializedName("EmailFO")
	val emailFO: Any? = null,

        @field:SerializedName("RoomType")
	val roomType: String? = null,

        @field:SerializedName("PnrId")
	val pnrId: String? = null,

        @field:SerializedName("LinkReference")
	val linkReference: Any? = null,

        @field:SerializedName("Phone")
	val phone: String? = null,

        @field:SerializedName("IsGuarantedBooking")
	val isGuarantedBooking: Boolean? = null,

        @field:SerializedName("RemarkReject")
	val remarkReject: Any? = null,

        @field:SerializedName("CancellationApi")
	val cancellationApi: String? = null,

        @field:SerializedName("TimeLimit")
	val timeLimit: String? = null,

        @field:SerializedName("MapUri")
	val mapUri: Any? = null,

        @field:SerializedName("CheckOutFormatString")
	val checkOutFormatString: String? = null,

        @field:SerializedName("ReffId")
	val reffId: Any? = null,

        @field:SerializedName("Amount")
	val amount: Double? = null,

        @field:SerializedName("TotalAmountApi")
	val totalAmountApi: Double? = null,

        @field:SerializedName("TotalAmount")
	val totalAmount: Double? = null,

        @field:SerializedName("IsWithBreakfast")
	val isWithBreakfast: Boolean? = null,

        @field:SerializedName("BookingCodeNew")
	val bookingCodeNew: Any? = null,

        @field:SerializedName("Facilities")
	val facilities: Any? = null,

        @field:SerializedName("Rooms")
	val rooms: Int? = null,

        @field:SerializedName("Contact")
	val contact: Contact? = null,

        @field:SerializedName("HotelKey")
	val hotelKey: String? = null,

        @field:SerializedName("AmountWithSf")
	val amountWithSf: Double? = null,

        @field:SerializedName("CauseViolatedRulesParagraf")
	val causeViolatedRulesParagraf: String? = null,

        @field:SerializedName("RoomCategory")
	val roomCategory: String? = null,

        @field:SerializedName("TimeLimitTripPlan")
	val timeLimitTripPlan: Any? = null,

        @field:SerializedName("DiscountPercentage")
	val discountPercentage: Int? = null,

        @field:SerializedName("TimeLimitFormatString")
	val timeLimitFormatString: String? = null,

        @field:SerializedName("TripItemId")
	val tripItemId: String? = null,

        @field:SerializedName("Checkin")
	val checkin: String? = null,

        @field:SerializedName("AmountPerNight")
	val amountPerNight: Double? = null
)