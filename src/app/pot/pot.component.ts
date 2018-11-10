import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-pot',
    templateUrl: './pot.component.html',
    styleUrls: ['./pot.component.css']
})
export class PotComponent implements OnInit {
    name: string;
    status: number;
    pots: any = [];

    constructor() {

    }

    ngOnInit() {
        this.pots.push({
            id: 0,
            status: 100,
            visible: true
        });
        this.pots.push({
            id: 1,
            status: 40,
            visible: false
        });
    }

    private switch(): void {
        this.pots.map(pot => {
            pot.visible = !pot.visible;
        });
    }

}
