# BankSystem

The system of bank payments. A user may have one or several bank accounts (deposit, credit). Access to your account can be obtained after entering your login and password. The user can make bank transfers, pay bills, display general information (account balance, recent transactions, expiration date). For Credit Accounts, information is also available on the credit limit, current debt, amount of accrued interest, interest rate. For Deposit accounts - deposit amount, rate, replenishment history. The user can submit a request to open a credit account, if there is none. The administrator confirms the opening of the account, taking into account the size of the deposit and the expiration date.

## Installation

mvn clean install

## Usage

mvn tomcat7:run

## P.S.: Don't be too strict. This is my very first independent project at the beginning of my journey in IT
