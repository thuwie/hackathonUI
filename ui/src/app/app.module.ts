import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from "@angular/common/http";
import {PotComponent} from './pot/pot.component';
import {SettingsComponent} from './settings/settings.component';
import {ChartComponent} from './chart/chart.component';
import { VkComponent } from './vk/vk.component';
import { PredictorComponent } from './predictor/predictor.component';

@NgModule({
    declarations: [
        AppComponent,
        PotComponent,
        SettingsComponent,
        ChartComponent,
        VkComponent,
        PredictorComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        NgbModule.forRoot(),
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
