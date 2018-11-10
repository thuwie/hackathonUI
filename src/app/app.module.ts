import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ChartsModule} from 'ng2-charts-x';
import {HttpClientModule} from "@angular/common/http";
import {PotComponent} from './pot/pot.component';
import {SettingsComponent} from './settings/settings.component';
import {ChartComponent} from './chart/chart.component';
import {NgCircleProgressModule} from "ng-circle-progress";
import { VkComponent } from './vk/vk.component';

@NgModule({
    declarations: [
        AppComponent,
        PotComponent,
        SettingsComponent,
        ChartComponent,
        VkComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        NgbModule.forRoot(),
        HttpClientModule,
        ChartsModule,
        NgCircleProgressModule.forRoot({
            // set defaults here
            radius: 100,
            outerStrokeWidth: 16,
            innerStrokeWidth: 8,
            outerStrokeColor: "#78C000",
            innerStrokeColor: "#C7E596",
            animationDuration: 300
        })
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
