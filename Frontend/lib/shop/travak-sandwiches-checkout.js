import DenTravakAbstractElement from '../travak-abstract-element.js';

class DenTravakSandwichesCheckout extends DenTravakAbstractElement {

    connectedCallback() {
        super.connectedCallback();
        this.initEventListeners();
    }

    init(sandwich) {
        this.sandwich = sandwich;
        this.byId('sandwiches').innerHTML = ``;
        this.byId('sandwiches').appendChild(htmlToElement(this.getSandwichTemplate(this.sandwich)));
    }

    initEventListeners() {
        this.byId('order-button').addEventListener('click', e => this.orderSandwich());
        this.byId('back-button').addEventListener('click', e => this.app().showSandwichList());
    }

    orderSandwich() {
        //todo: call backend via fetch api
        console.log("sommething" + this.getOrder());

        console.log(this.sandwich.sandwichId);
        
        fetch('http://localhost:8080/orders', {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
            body: JSON.stringify(this.getOrder())
        })
        .then(function (data) {  
            console.log('Request success: ', data);  
         })  
        .catch(function (error) {  
            console.log('Request failure: ', error);  
        });
        this.app().dispatchEvent(new CustomEvent('order-succeeded', {detail: this.sandwich}));
    }

    getOrder(){
        //var breadType = document.querySelector('input[name=typeBrood]:checked').value;
        //var breadType = "WRAP";
        //var number = document.getElementById('mobile-phone-number').Value();
        //var number = "0487/12 34 56";
        //var id = "833758b0-1b06-4eee-9fe3-2360812dbb66";
        //return "{\"sandwichId\": \"" + id + "\", \"name\": \"" + this.sandwich.name + "\", \"breadType\": \"" + breadType + "\", \"price\": " + this.sandwich.price + ", \"mobilePhoneNumber\": \"" + number + "\"}";
        return "{" +
            "\"sandwichId\": \"aa4d0f62-4712-4dcb-a1c4-a28f8ba46ef5\"," +
            "\"name\": \"Smos\"," +
            "\"breadType\": \"WRAP\","+
            "\"price\": 3.60,"+
            "\"mobilePhoneNumber\": \"0487\/123456\""+
        "}";
    }

    get template() {
        return `
            <style>
                .form-group {
                    margin-bottom: 2rem;
                }
                .dt-header {
                    display: flex;
                }
                .dt-header button {
                    margin-left: auto;
                }
                div.dt-sandwich-info {
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <div class="dt-header">
                    <h3>Welkom bij den Travak</h3>
                    <button id="back-button" type="button" class="btn btn-primary">Terug</button>
                </div>
                <h4>Je geselecteerde broodje</h4>
                <div>
                <ul id="sandwiches" class="list-group"></ul>
                </div>
                <div class="form-group">
                    <label for="typeBrood"><h4>Kies het type brood</h4></label>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioBoterhammekes" value="BOTERHAMMEKES">
                        <label class="form-check-label" for="radioBoterhammekes">
                            Boterhammekes
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioWrap" value="WRAP">
                        <label class="form-check-label" for="radioWrap">
                            Wrap
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="typeBrood" id="radioTurksBrood" value="TURKS_BREAD">
                        <label class="form-check-label" for="radioTurksBrood">
                            Turks brood
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="mobile-phone-number"><h4>Je GSM Nummer</h4></label>
                    <input type="text" class="form-control" id="mobile-phone-number" placeholder="0487/12 34 56">
                </div>

                <button id="order-button" class="btn btn-primary active">Bestellen</button>
            </div>
        `;
    }

    getSandwichTemplate(sandwich) {
        return `
            <a class="list-group-item">
                <button type="button" class="btn btn-primary bmd-btn-fab">
                    ${sandwich.name.charAt(0)}
                </button>
                <div class="bmd-list-group-col">
                    <p class="list-group-item-heading">${sandwich.name}</p>
                    <p class="list-group-item-text">${sandwich.ingredients}</p>
                </div>
                <div class="dt-sandwich-info">
                    <p class="list-group-item-text">${sandwich.price}</p>
                </div>
            </a>
        `;
    }
}

customElements.define('travak-sandwiches-checkout', DenTravakSandwichesCheckout);