import {Component, ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts-x";

@Component({
    selector: 'app-chart',
    templateUrl: './chart.component.html',
    styleUrls: ['./chart.component.css']
})
export class ChartComponent { // lineChart
    @ViewChild("baseChart") chart: BaseChartDirective;
    private ChartData: any = [{data: 0, label: 'X'}];
    public lineChartData: Array<any> = [
        {data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0], label: 'Coffee'}
    ];
    public lineChartLabels: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'];
    public lineChartOptions: any = {
        responsive: true
    };
    public lineChartColors: Array<any> = [
        { // grey
            backgroundColor: 'rgba(148,159,177,0.2)',
            borderColor: 'rgba(148,159,177,1)',
            pointBackgroundColor: 'rgba(148,159,177,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(148,159,177,0.8)'
        }
    ];
    public lineChartLegend: boolean = false;
    public lineChartType: string = 'line';

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

    public updateChart(data: any) {
        console.log(this.lineChartData);
        let temp = data.map(obj => Math.round(obj.percent * 100));
        this.lineChartData[0].data = temp;
        let temp2 = data.map(obj => {
            return new Date(obj.timestamp).toDateString();
        });
        this.lineChartLabels = temp;
        if (this.chart !== undefined) {
            this.chart.chart.destroy();
            this.chart.chart = 0;

            this.chart.datasets = temp;
            this.chart.labels = temp2;
            this.chart.ngOnInit();
        }


    }
}