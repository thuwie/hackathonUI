import {Component, OnInit} from '@angular/core';
import {ConnectorService} from "../connector.service";
import {interval} from "rxjs";

@Component({
    selector: 'app-predictor',
    templateUrl: './predictor.component.html',
    styleUrls: ['./predictor.component.css']
})
export class PredictorComponent implements OnInit {
    indicators: any = [];

    constructor(private connectorService: ConnectorService) {
        this.indicators.push({status: true});
        this.indicators.push({status: false});
        this.indicators.push({status: false});
    }

    ngOnInit() {
        interval(5000).subscribe(() => {
            this.connectorService.getPrediction().subscribe(data => {
                for (let i = 0; i < 3; i++) {
                    this.indicators[i].status = i === data;
                }
            });

        });
    }
}
