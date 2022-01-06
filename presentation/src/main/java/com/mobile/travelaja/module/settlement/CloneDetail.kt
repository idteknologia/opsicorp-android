package com.mobile.travelaja.module.settlement

import opsigo.com.domainlayer.model.settlement.DetailSettlement

object CloneDetail {
    fun cloneDetail(detail: DetailSettlement) : DetailSettlement {
        val clone = DetailSettlement()
        clone.Id = detail.Id
        clone.Code = detail.Code
        clone.BankTransfer = detail.BankTransfer
        clone.BankAccount = detail.BankAccount
        clone.TripId = detail.TripId
        clone.TripCode = detail.TripCode
        clone.StartDate = detail.StartDate
        clone.EndDate = detail.EndDate
        clone.CompanyCode = detail.CompanyCode
        clone.CompanyName = detail.CompanyName
        clone.Golper = detail.Golper
        clone.DurationDay = detail.DurationDay
        clone.Purpose = detail.Purpose
        clone.RouteType = detail.RouteType
        clone.TripType = detail.TripType
        clone.AmountLaundry = detail.AmountLaundry
        clone.CurrLaundry = detail.CurrLaundry
        clone.AmountAllowance = detail.AmountAllowance
        clone.AmountAllowanceSubmit = detail.AmountAllowanceSubmit
        clone.CurrAllowance = detail.CurrAllowance
        clone.SpecificAreaTariff = detail.SpecificAreaTariff
        clone.SpecificAreaDays = detail.SpecificAreaDays
        clone.UseSpecificArea = detail.UseSpecificArea
        clone.IsStaySpecArea = detail.IsStaySpecArea
        clone.CurrSpecificArea = detail.CurrSpecificArea
        clone.TotalSpecificAreaExpenseSubmit = detail.TotalSpecificAreaExpenseSubmit
        clone.TotalSpecificAreaExpense = detail.TotalSpecificAreaExpense
        clone.TransportExpenses = detail.TransportExpenses
        clone.OtherTransportExpenses = detail.OtherTransportExpenses
        clone.OtherExpense = detail.OtherExpense
        clone.Attachments = detail.Attachments
        clone.TicketRefunds = detail.TicketRefunds
        clone.TotalTransportExpense = detail.TotalTransportExpense
        clone.TotalTransportExpenseUsd = detail.TotalTransportExpenseUsd
        clone.TotalOtherTransportExpense = detail.TotalOtherTransportExpense
        clone.TotalOtherTransportExpenseUsd = detail.TotalOtherTransportExpenseUsd
        clone.TotalOtherExpense = detail.TotalOtherExpense
        clone.TotalOtherExpenseUsd = detail.TotalOtherExpenseUsd
        clone.TotalRefundTicket = detail.TotalRefundTicket
        clone.TotalRefundTicketUsd = detail.TotalRefundTicketUsd
        clone.TotalCashAdvance = detail.TotalCashAdvance
        clone.CashAdvanceCurr = detail.CashAdvanceCurr
        clone.TotalExpenseSubmit = detail.TotalExpenseSubmit
        clone.TotalExpenseSubmitUsd = detail.TotalExpenseSubmitUsd
        clone.TotalExpenseVerification = detail.TotalExpenseVerification
        clone.TotalExpenseVerificationUsd = detail.TotalExpenseVerificationUsd
        clone.TotalToBePaidIdr = detail.TotalToBePaidIdr
        clone.TotalToBePaidUsd = detail.TotalToBePaidUsd
        clone.CreatedDateView = detail.CreatedDateView
        clone.CreatedBy = detail.CreatedBy
        clone.EmployeeNik = detail.EmployeeNik
        clone.EmployeeName = detail.EmployeeName
        clone.PositionName = detail.PositionName
        clone.Status = detail.Status
        clone.StatusView = detail.StatusView
        clone.Comment = detail.Comment
        clone.TripItemTypes = detail.TripItemTypes
        clone.PartnerName = detail.PartnerName
        clone.Routes = detail.Routes
        clone.ShowButtonApprove = detail.ShowButtonApprove
        clone.IsReviseAll = detail.IsReviseAll
        clone.Histories = detail.Histories
        return clone
    }

}