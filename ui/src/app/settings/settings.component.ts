import {Component, OnInit} from '@angular/core';


@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    status: boolean = false;
    togglers: any = [];

    constructor() {
        this.togglers.push({
            option: 'coffee',
            status: false
        });
        this.togglers.push({
            option: 'attract',
            status: false
        });

    }

    ngOnInit() {
    }

    clickEvent(option: any) {
        console.log(option);
        option.status = !option.status;
    }
}
