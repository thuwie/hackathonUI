import {Component, HostBinding, OnInit} from '@angular/core';
import {trigger, state, style, animate, transition, keyframes, stagger} from "@angular/animations";
import {ConnectorService} from "../connector.service";
import {interval} from "rxjs";

@Component({
    selector: 'app-pot',
    templateUrl: './pot.component.html',
    styleUrls: ['./pot.component.css'],
    animations: [trigger('visibilityChanged', [
        transition(':enter', [
            style({opacity: 0, transform: 'translateX(-80px)'}),
            animate(300, style({opacity: 1, transform: 'translateX(0)'}))
        ]),
        transition(':leave', [
            style({opacity: 1, transform: 'translateX(0)'}),
            animate(300, style({opacity: 0, transform: 'translateX(80px)'}))
        ])
    ]),
        trigger('move', [
            state('in', style({transform: 'translateX(0)'})),
            state('out', style({transform: 'translateX(100%)'})),
            transition('in => out', animate('50s linear')),
            transition('out => in', animate('50s linear'))
        ])
    ]
})
export class PotComponent implements OnInit {
    private state = 'in';
    private pots: any = [];
    private potId = 1;
    public latestHistoryData: any = [];
    public latestVolumeData: any = [];

    constructor(private connectorService: ConnectorService) {
        this.startWatch();
    }

    ngOnInit() {
        this.pots.push({
            id: 0,
            status: 100,
            visible: true,
        });
        this.pots.push({
            id: 1,
            status: 40,
            visible: false
        });
        this.latestVolumeData = [{}, {}];
        // setTimeout(() => {
        //     this.state = 'out';
        // }, 0);
    }

    private startWatch(): void {
        interval(5000).subscribe(() => {
            this.connectorService.getLatest().subscribe(data => {
                console.log(data);
                let id = data.camera_id;
                if (this.latestVolumeData[id].timestamp !== data.timestamp) {
                    this.latestVolumeData[id] = data;
                    this.pots[id].status = Math.round(data.percent * 100);
                    this.updateHistory(id);
                }
            });
        });
    }

    private updateHistory(index: number) {
        this.connectorService.getLatestHistoryData(index).subscribe(historyData => {
            console.info('Update history for ' + index);
            this.latestHistoryData[index] = historyData;
        });
    }


    private switch(): void {
        this.potId = this.potId === 1 ? 0 : 1;
        this.pots.map(pot => {
            pot.visible = !pot.visible;
        });
    }

    onEnd(event) {
        this.state = 'in';
        if (event.toState === 'in') {
            setTimeout(() => {
                this.state = 'out';
            }, 0);
        }
    }

}
