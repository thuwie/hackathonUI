import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ConnectorService} from "../connector.service";

@Component({
    selector: 'app-vk',
    templateUrl: './vk.component.html',
    styleUrls: ['./vk.component.css']
})
export class VkComponent implements OnInit {
    vkIdNumber: number;
    constructor(private connectorService: ConnectorService,
                private modalService: NgbModal) {
    }

    ngOnInit() {
    }

    openSubscribeModal(content) {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then(() => {
            this.connectorService.subscribeInVk(this.vkIdNumber).subscribe(data => {

            }, (error) => {
                console.log("Unable to subscribe in VK :(");
                console.log(error);
            });
            this.vkIdNumber = undefined;
        }, () => {

        });
    }
}
