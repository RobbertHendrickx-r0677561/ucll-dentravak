import DenTravakAbstractElement from '../travak-abstract-element.js';

class DenTravakOrderList extends DenTravakAbstractElement {

    constructor() {
        super('travak-admin-app')
    }

    connectedCallback() {
        super.connectedCallback();
        fetch('http://193.191.177.8:10218/den-travak/orders')
            .then(resp => resp.json())
            .then(json => this.updateOrderList(json));
        this.initEventListeners();
    }

    initEventListeners() {
        this.byId('edit-sandwiches-btn').addEventListener('click', (e) => this.app().showSandwichList());
    }

    updateOrderList(orders) {
        let orderList = this.byId('orders');
        orderList.innerHTML = ``;
        orders.forEach(order => {
            let orderl = htmlToElement(this.getOrderTemplate(order));
            orderList.appendChild(orderl);
        });
    }

    get template() {
        return `
            <style>
                div.dt-order-info {
                    margin-left: auto;
                }
                .bmd-list-group-col {
                    width: 70%;
                }
                p.list-group-item-heading {
                    display:flex;
                    justify-content: space-between;
                }
                span.creationDate {
                    display:inline-block;
                    float: right;
                }
                .travak-header {
                    display: flex;
                }
                .travak-header button {
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <div class="travak-header">
                    <h4>Den Travak Bestellingen</h4>
                    <button id="edit-sandwiches-btn" type="button" class="btn btn-primary">Bewerk broodjeslijst</button>
                </div>
                <div>
                <ul id="orders" class="list-group">
                </ul>
                </div>
                <a class="button" href="./download-orders.csv">Download bestellingen van vandaag</a>
            </div>
        `;
    }

    getOrderTemplate(order) {
        return `
            <a class="list-group-item">
                <button type="button" class="btn btn-primary bmd-btn-fab">
                    ${order.name.charAt(0)}
                </button>
                <div class="bmd-list-group-col">
                    <p class="list-group-item-heading">${order.mobilePhoneNumber}<span class="creationDate">${dateFns.distanceInWordsToNow(order.creationDate)} ago</span></p>
                    <p class="list-group-item-text">${order.name} - ${order.breadType.toLowerCase()}</p>
                </div>
                <div class="dt-order-info">
                    <p class="list-group-item-text">PRINTED=${order.printed}</p>
                </div>
                <div class="dt-order-info">
                    <p class="list-group-item-text">${order.price}</p>
                </div>
            </a>
        `;
    }
}

customElements.define('travak-order-list', DenTravakOrderList);