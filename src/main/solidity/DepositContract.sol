// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.7.0;

//contract Mortal
//contract DepositContract

contract Mortal {
    /* Define variable owner of the type address*/
    address owner;

    /* this function is executed at initialization and sets the owner of the contract */
    constructor () {owner = msg.sender;}

    modifier onlyOwner {
        require(
            msg.sender == owner,
            "Only owner can call this function."
        );
        _;
    }

    /* Function to recover the funds on the contract
    https://docs.soliditylang.org/en/develop/introduction-to-smart-contracts.html#deactivate-and-self-destruct
    https://ethereum.stackexchange.com/questions/54660/how-selfdestruct-works
    TODO implement check that balances are zero before selfdestruct
    */
    function kill() onlyOwner public {
        selfdestruct( payable(msg.sender));
    }
}

contract DepositContract is Mortal {
    /* current interest rate for new deposits */
    uint interestRate;

    struct DepositRecord {
        address payable client;   // About payable https://betterprogramming.pub/solidity-0-6-you-might-be-sending-ether-all-wrong-1e119e1ffc27
        uint amount;
        // currency is currency of network
        uint depositDate;
        uint interestRate; // interest rate when deposit was made
        uint maturationDate; // date to return deposit + earned interests
    }
    //mapping (address => DepositRecord) private _deposits; //storage
    DepositRecord[] private _deposits; //storage
    uint private _lastDepositIndex;
    uint numDeposits; //initialized to 0

    bool active;

    /* this runs when the contract is executed */
    constructor (uint  _interestRate) { //memory
        interestRate = _interestRate;
    }

    event InterestRateModified(
        uint indexed oldInterestRateIdx, uint indexed newInterestRateIdx,
        uint oldInterestRate, uint newInterestRate);

    function changeInterestRate(uint  _interestRate) onlyOwner public { //memory
        emit InterestRateModified(interestRate, _interestRate, interestRate, _interestRate);
        interestRate = _interestRate;
    }

    function viewInterestRate() public view returns (uint)  { //memory
        return interestRate;
    }

    function makeDeposit(uint  _amount) public payable  {
        address payable _client = payable( msg.sender );
        //uint amount =  msg.value //Note no need for amount parameter?

        // TODO
        //DepositRecord storage rec = _deposits[client];
        DepositRecord memory rec =  DepositRecord({  //TODO should it be storage? 
            client : _client,
            amount : _amount,
            //rec.depositDate = today;
            depositDate: 0,
            interestRate: interestRate,
            //rec.maturationDate = today + 365;
            maturationDate: 0
        }  ) ;
        //_deposits.push( client, rec );
        _deposits[_lastDepositIndex++] = rec;
        numDeposits++;
    }

    function fun1(DepositRecord storage rec) private {
        uint amount = rec.amount;
        if (address(this).balance > amount){

            address payable client = rec.client;
            client.transfer(amount); // TODO this can get exception
            numDeposits--;
        }

    }

}
