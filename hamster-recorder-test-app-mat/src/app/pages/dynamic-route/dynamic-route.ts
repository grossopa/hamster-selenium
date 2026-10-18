import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  imports: [
    CommonModule,
    MatCardModule, MatButtonModule, MatChipsModule, MatFormFieldModule, MatInputModule,
  ],
  selector: 'app-dynamic-route',
  templateUrl: './dynamic-route.html',
})
export class DynamicRouteComponent {
  id: string;
  isUser: boolean;
  entity: string;

  constructor(private route: ActivatedRoute) {
    this.id = this.route.snapshot.paramMap.get('id') ?? '0';
    this.isUser = window.location.pathname.startsWith('/users');
    this.entity = this.isUser ? 'User' : 'Product';
  }
}
